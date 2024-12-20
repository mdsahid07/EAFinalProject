package edu.miu.cs.cs544.sahid.hotelbookingsystem.controller;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel.ReservationDTO;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel.ReservationPaymentRequest;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Reservation;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<String> createReservationsWithPayment(
            @RequestBody ReservationPaymentRequest request) {
        String reservations = reservationService.createReservationsWithPayment(
                request.getRoomIds(),
                request.getReservation(),
                request.getPayment(),
                request.getGuests()
        );
        return ResponseEntity.badRequest().body(reservations);
    }

//    @GetMapping
//    public ResponseEntity<List<Reservation>> getAllReservations() {
//        // For demonstration: Allowing only authenticated users with any role to view reservations
//        return ResponseEntity.ok(reservationService.getAllReservations());
//    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(value = "userId", required = false) Long userId) {

        List<ReservationDTO> reservations = reservationService.getAllReservationsWithFilters(fromDate, toDate, userId);
        return ResponseEntity.ok(reservations);
    }
}


//{
//        "reservation": {
//        "checkInDate": "2024-12-20",
//        "checkOutDate": "2024-12-25",
//        "numberOfGuests": 3
//        },
//        "payment": {
//        "paymentMethod": "Credit Card"
//        },
//        "roomIds": [101, 102, 103],
//        "guests": [
//        {
//        "fullName": "John Doe",
//        "email": "john.doe@example.com",
//        "phone": "123-456-7890",
//        "address": "123 Main St, City, Country",
//        "dateOfBirth": "1985-05-20"
//        },
//        {
//        "fullName": "Jane Doe",
//        "email": "jane.doe@example.com",
//        "phone": "123-456-7891",
//        "address": "123 Main St, City, Country",
//        "dateOfBirth": "1990-06-15"
//        },
//        {
//        "fullName": "Alice Smith",
//        "email": "alice.smith@example.com",
//        "phone": "123-456-7892",
//        "address": "456 Elm St, City, Country",
//        "dateOfBirth": "1980-07-10"
//        }
//        ]
//        }
