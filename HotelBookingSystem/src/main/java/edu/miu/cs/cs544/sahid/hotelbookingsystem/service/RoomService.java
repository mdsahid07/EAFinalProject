package edu.miu.cs.cs544.sahid.hotelbookingsystem.service;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel.RoomDTO;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Hotel;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Room;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.repository.HotelRespository;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRespository hotelRepository;

    // Create a new room
    public Room createRoom(Long hotelId, Room room) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + hotelId));
        room.setHotel(hotel);
        Room savedRoom =roomRepository.save(room);
        return savedRoom;

    }

    // Retrieve all rooms
//    public List<Room> getAllRooms() {
//        return roomRepository.findAll();
//    }
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream().filter(a->a.isAvailable()).map(room -> {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(room.getId());
            roomDTO.setRoomType(room.getRoomType());
            roomDTO.setBedType(room.getBedType());
            roomDTO.setRoomNumber(room.getRoomNumber());
            roomDTO.setPricePerNight(room.getPricePerNight());
            roomDTO.setIsAvailable(room.isAvailable());
            roomDTO.setHotelName(room.getHotel().getName()); // Fetch hotel name
            return roomDTO;
        }).collect(Collectors.toList());
    }

    // Retrieve rooms by hotel ID
    public List<Room> getRoomsByHotelId(Long hotelId) {
        List<Room> availableRooms = roomRepository.findAvailableRoomsByHotel(hotelId);
        return availableRooms;
    }

    // Retrieve a room by ID
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + id));
    }

    // Update a room
    public Room updateRoom(Long id, Room roomDetails) {
        Room room = getRoomById(id); // Fetch the existing room by ID

        // Update only the fields that are provided in `roomDetails`
        if (roomDetails.getRoomType() != null) {
            room.setRoomType(roomDetails.getRoomType());
        }
        if (roomDetails.getBedType() != null) {
            room.setBedType(roomDetails.getBedType());
        }
        if (roomDetails.getRoomNumber() != null) {
            room.setRoomNumber(roomDetails.getRoomNumber());
        }
        if (roomDetails.getPricePerNight() != 0) {
            room.setPricePerNight(roomDetails.getPricePerNight());
        }
        if (roomDetails.isAvailable() != room.isAvailable()) {
            room.setAvailable(roomDetails.isAvailable());
        }

        return roomRepository.save(room); // Save the updated entity
    }

    // Delete a room
    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }
}
