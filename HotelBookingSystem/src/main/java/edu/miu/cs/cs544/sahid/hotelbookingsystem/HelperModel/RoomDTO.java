package edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel;

public class RoomDTO {
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private String hotelName;
    private String hotelCity;
    private boolean isAvailable;

    public RoomDTO() {}
    // Constructor, Getters, and Setters
    public RoomDTO(String roomNumber, String roomType, double pricePerNight, String hotelName, String hotelCity, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.hotelName = hotelName;
        this.hotelCity = hotelCity;
        this.isAvailable = isAvailable;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelCity() {
        return hotelCity;
    }

    public void setHotelCity(String hotelCity) {
        this.hotelCity = hotelCity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
