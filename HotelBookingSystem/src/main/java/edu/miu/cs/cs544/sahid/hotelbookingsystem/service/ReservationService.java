package edu.miu.cs.cs544.sahid.hotelbookingsystem.service;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel.GuestDTO;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel.ReservationDTO;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.*;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.repository.*;
import jakarta.jms.JMSProducer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRespository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private JmsProducer jmsProducer;
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @PersistenceContext
    private EntityManager entityManager;

    public ReservationService(ReservationRepository reservationRepository, UserRespository userRepository, RoomRepository roomRepository, GuestRepository guestRepository, JmsProducer jmsProducer) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.jmsProducer = jmsProducer;
    }

    @Transactional
    public String createReservationsWithPayment(List<Long> roomIds, Reservation reservationDetails, Payment paymentDetails, List<Guest> guestDetails) {
        try {
            logger.info("Reservation process started...");
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

            List<Room> rooms = roomRepository.findAllById(roomIds);
            if (rooms.isEmpty() || rooms.size() != roomIds.size()) {
                return "Some rooms are not available.";
            }

            for (Long roomId : roomIds) {
                Room room = roomRepository.findRoomForUpdate(roomId);
                if (!room.isAvailable()) {
                    throw new RuntimeException("Room is already booked.");
                }
            }

            List<Reservation> reservations = new ArrayList<>();
            double totalPrice = 0.0;

            for (Room room : rooms) {
                Reservation reservation = new Reservation();
                reservation.setUser(user);
                reservation.setRoom(room);
                reservation.setCheckInDate(reservationDetails.getCheckInDate());
                reservation.setCheckOutDate(reservationDetails.getCheckOutDate());
                reservation.setNumberOfGuests(reservationDetails.getNumberOfGuests());
                reservation.setTotalPrice(room.getPricePerNight());
                reservation.setStatus("Confirmed");
                reservation.setBookingDate(LocalDate.now());

                room.setAvailable(false);
                roomRepository.save(room);
                Reservation savedReservation = reservationRepository.save(reservation);

                // Associate guests with the saved reservation
                for (Guest guest : guestDetails) {
                    guest.setReservation(savedReservation);
                }
                guestRepository.saveAll(guestDetails);

                reservations.add(savedReservation);
                totalPrice += room.getPricePerNight();
            }

            // Create and save payment
            Payment payment = paymentService.processPayment(paymentDetails, totalPrice);

            // Associate payment with reservations
            for (Reservation reservation : reservations) {
                reservation.setPayment(payment);
                reservationRepository.save(reservation);
            }

            //JMS message send
            try {
                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("Reservation confirmed for user: ").append(user.getUsername())
                        .append("\nTotal Price: ").append(totalPrice)
                        .append("\nCheck-In Date: ").append(reservationDetails.getCheckInDate())
                        .append(", Check-Out Date: ").append(reservationDetails.getCheckOutDate())
                        .append("\nRooms and Hotels:\n");

                for (Room room : rooms) {
                    messageBuilder.append("Room Number: ").append(room.getRoomNumber())
                            .append(", Room Type: ").append(room.getRoomType())
                            .append(", Hotel: ").append(room.getHotel().getName())
                            .append("\n");
                }

                String message = messageBuilder.toString();

                // Send JMS message
                jmsProducer.sendReservationConfirmation("reservationQueue", message);
                System.out.println("JMS message sent successfully.");
            }
            catch (Exception e) {
                System.out.println("JMS message sending failed due to: "+e.getMessage());
            }
            return "Reservation created successfully.";
        } catch (Exception e) {
            logger.error("Error during reservation creation: {}", e.getMessage(), e);
            return "Reservation creation failed.";
        }
    }

    public List<ReservationDTO> getAllReservationsWithFilters(LocalDate fromDate, LocalDate toDate, Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = query.from(Reservation.class);

        // Join with Room and Hotel for details
        Join<Reservation, Room> roomJoin = reservationRoot.join("room", JoinType.INNER);
        Join<Room, Hotel> hotelJoin = roomJoin.join("hotel", JoinType.INNER);

        // Build predicates
        List<Predicate> predicates = new ArrayList<>();

        if (fromDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(reservationRoot.get("checkInDate"), fromDate));
        }
        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(reservationRoot.get("checkOutDate"), toDate));
        }
        if (userId != null) {
            predicates.add(cb.equal(reservationRoot.get("user").get("id"), userId));
        }

        query.select(reservationRoot).where(predicates.toArray(new Predicate[0]));

        // Execute query
        List<Reservation> reservations = entityManager.createQuery(query).getResultList();

        // Convert to DTO
        return reservations.stream().map(reservation -> {
            List<GuestDTO> guests = reservation.getGuests().stream()
                    .map(guest -> new GuestDTO(
                            guest.getFullName(),
                            guest.getEmail(),
                            guest.getPhone(),
                            guest.getAddress(),
                            guest.getDateOfBirth()))
                    .collect(Collectors.toList());

            Room room = reservation.getRoom();
            return new ReservationDTO(
                    reservation.getId(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate(),
                    reservation.getNumberOfGuests(),
                    reservation.getTotalPrice(),
                    reservation.getStatus(),
                    reservation.getBookingDate(),
                    room.getHotel().getName(),
                    room.getHotel().getCity(),
                    room.getRoomType(),
                    room.getRoomNumber(),
                    room.getPricePerNight(),
                    guests
            );
        }).collect(Collectors.toList());
    }

//    public List<ReservationDTO> getAllReservations() {
//        List<ReservationDTO> reservations = reservationRepository.findAllReservationsWithDetails();
//
//        for (ReservationDTO reservation : reservations) {
//            List<GuestDTO> guests = guestRepository.findByReservationId(reservation.getReservationId())
//                    .stream()
//                    .map(guest -> new GuestDTO(guest.getFullName(), guest.getEmail(), guest.getPhone(), guest.getAddress(), guest.getDateOfBirth()))
//                    .collect(Collectors.toList());
//            reservation.setGuests(guests);
//        }
//
//        return reservations;
//    }


    // Retrieve a reservation by ID
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));
    }

}
