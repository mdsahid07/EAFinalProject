package edu.miu.cs.cs544.sahid.hotelbookingsystem.repository;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel.ReservationDTO;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

//    @Query("""
//        SELECT new edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel.ReservationDTO(
//            r.id, r.checkInDate, r.checkOutDate, r.numberOfGuests, r.totalPrice,
//            r.status, r.bookingDate,
//            h.name, h.city,
//            ro.roomType, ro.roomNumber, ro.pricePerNight
//        )
//        FROM Reservation r
//        JOIN r.room ro
//        JOIN ro.hotel h
//    """)
//    List<ReservationDTO> findAllReservationsWithDetails();
}
