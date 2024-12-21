package edu.miu.cs.cs544.sahid.hotelbookingsystem.service;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel.GuestDTO;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel.ReservationDTO;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel.RoomDTO;
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
import java.util.UUID;
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

            for (Room room : rooms) {
                Room roomForUpdate = roomRepository.findRoomForUpdate(room.getId());
                if (!roomForUpdate.getAvailable()) {
                    throw new RuntimeException("Room " + room.getRoomNumber() + " is already booked.");
                }
                roomForUpdate.setAvailable(false);
                roomRepository.save(roomForUpdate);
            }
            String bookingNumber = generateBookingNumber();
            // Create a single reservation
            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setRoom(rooms);
            reservation.setCheckInDate(reservationDetails.getCheckInDate());
            reservation.setCheckOutDate(reservationDetails.getCheckOutDate());
            reservation.setNumberOfGuests(reservationDetails.getNumberOfGuests());
            reservation.setTotalPrice(rooms.stream().mapToDouble(Room::getPricePerNight).sum());
            reservation.setStatus("Confirmed");
            reservation.setBookingDate(LocalDate.now());
            reservation.setBookingNumber(bookingNumber);
            Reservation savedReservation = reservationRepository.save(reservation);

            // Associate guests with the single reservation
            guestDetails.forEach(guest -> guest.setReservation(savedReservation));
            guestRepository.saveAll(guestDetails);

            // Process payment
            double totalPrice = reservation.getTotalPrice();
            Payment payment = paymentService.processPayment(paymentDetails, totalPrice);
            reservation.setPayment(payment);
            reservationRepository.save(reservation);

            // Send JMS message
            try {
                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("Reservation confirmed for user: ").append(user.getUsername())
                        .append("\nTotal Price: ").append(totalPrice)
                        .append("\nCheck-In Date: ").append(reservationDetails.getCheckInDate())
                        .append(", Check-Out Date: ").append(reservationDetails.getCheckOutDate())
                        .append(", Reservation Number: ").append(bookingNumber)
                        .append("\nRooms and Hotels:\n");

                for (Room room : rooms) {
                    messageBuilder.append("Room Number: ").append(room.getRoomNumber())
                            .append(", Room Type: ").append(room.getRoomType())
                            .append(", Hotel: ").append(room.getHotel().getName())
                            .append("\n");
                }

                String message = messageBuilder.toString();
                jmsProducer.sendReservationConfirmation("reservationQueue", message);
            } catch (Exception e) {
                logger.error("JMS message sending failed: {}", e.getMessage(), e);
            }

            return "Reservation created successfully.";
        } catch (Exception e) {
            logger.error("Error during reservation creation: {}", e.getMessage(), e);
            return "Reservation creation failed.";
        }
    }
    private String generateBookingNumber() {
        String timestamp = Long.toString(System.currentTimeMillis(), 36).toUpperCase();
        String randomString = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 7).toUpperCase();
        return (timestamp + randomString).substring(0, 15);
    }

    public List<ReservationDTO> getAllReservationsWithFilters(LocalDate fromDate, LocalDate toDate, Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = query.from(Reservation.class);

        Join<Reservation, Room> roomJoin = reservationRoot.join("rooms", JoinType.INNER); // Adjusted for multiple rooms per reservation
        Join<Room, Hotel> hotelJoin = roomJoin.join("hotel", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        if (fromDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(reservationRoot.get("bookingDate"), fromDate));
        }
        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(reservationRoot.get("bookingDate"), toDate));
        }
        if (userId != null) {
            predicates.add(cb.equal(reservationRoot.get("user").get("id"), userId));
        }

        query.select(reservationRoot).where(cb.and(predicates.toArray(new Predicate[0])));

        // Execute query
        List<Reservation> reservations = entityManager.createQuery(query).getResultList();

        // Convert to DTO
        return reservations.stream().map(reservation -> {
            // Gather guest details
            List<GuestDTO> guests = reservation.getGuests().stream()
                    .map(guest -> new GuestDTO(
                            guest.getFullName(),
                            guest.getEmail(),
                            guest.getPhone(),
                            guest.getAddress(),
                            guest.getDateOfBirth()))
                    .collect(Collectors.toList());

            // Map multiple rooms to include in the DTO
            List<RoomDTO> roomDetails = reservation.getRoom().stream()
                    .map(room -> new RoomDTO(
                            room.getRoomNumber(),
                            room.getRoomType(),
                            room.getPricePerNight(),
                            room.getHotel().getName(),
                            room.getHotel().getCity(), room.getAvailable()))
                    .collect(Collectors.toList());

            return new ReservationDTO(
                    reservation.getId(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate(),
                    reservation.getNumberOfGuests(),
                    reservation.getTotalPrice(),
                    reservation.getStatus(),
                    reservation.getBookingDate(),
                    reservation.getBookingNumber(),
                    roomDetails, // Added room details
                    guests // Added guest details

            );
        }).collect(Collectors.toList());
    }

    // Retrieve a reservation by ID
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));
    }

}
