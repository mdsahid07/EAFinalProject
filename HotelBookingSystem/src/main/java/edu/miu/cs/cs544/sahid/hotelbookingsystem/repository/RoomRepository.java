package edu.miu.cs.cs544.sahid.hotelbookingsystem.repository;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(name = "Room.findAvailableRoomsByHotel")
    List<Room> findAvailableRoomsByHotel(@Param("hotelId") Long hotelId);

    @Query("SELECT r FROM Room r WHERE r.id = :roomId")
    @Lock(LockModeType.PESSIMISTIC_WRITE) // Pessimistic locking
    Room findRoomForUpdate(@Param("roomId") Long roomId);
}
