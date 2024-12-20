package edu.miu.cs.cs544.sahid.hotelbookingsystem.service;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Hotel;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.repository.HotelRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private final HotelRespository hotelRepository;
    public HotelService(HotelRespository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    // Retrieve all hotels
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    // Retrieve a hotel by ID
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + id));
    }

    // Update an existing hotel
    public Hotel updateHotel(Long id, Hotel hotelDetails) {
        Hotel hotel = getHotelById(id);
        // Update only the fields that are provided in the `hotelDetails`
        if (hotelDetails.getName() != null) {
            hotel.setName(hotelDetails.getName());
        }
        if (hotelDetails.getAddress() != null) {
            hotel.setAddress(hotelDetails.getAddress());
        }
        if (hotelDetails.getCity() != null) {
            hotel.setCity(hotelDetails.getCity());
        }
        if (hotelDetails.getContactInfo() != null) {
            hotel.setContactInfo(hotelDetails.getContactInfo());
        }
        if (hotelDetails.getRating() != 0) {
            hotel.setRating(hotelDetails.getRating());
        }
        return hotelRepository.save(hotel);
    }

    // Delete a hotel
    public void deleteHotel(Long id) {
        Hotel hotel = getHotelById(id);
        hotelRepository.delete(hotel);
    }
}
