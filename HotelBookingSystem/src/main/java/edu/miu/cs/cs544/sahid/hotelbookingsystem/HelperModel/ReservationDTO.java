package edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel;

import java.time.LocalDate;
import java.util.List;

public class ReservationDTO {
    private Long reservationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private double totalPrice;
    private String status;
    private LocalDate bookingDate;

    private String hotelName;
    private String hotelCity;

    private String roomType;
    private String roomNumber;
    private double roomPricePerNight;

    private List<GuestDTO> guests;

    protected ReservationDTO() {
    }

    public ReservationDTO(
            Long reservationId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int numberOfGuests,
            double totalPrice,
            String status,
            LocalDate bookingDate,
            String hotelName,
            String hotelCity,
            String roomType,
            String roomNumber,
            double roomPricePerNight,
            List<GuestDTO> guests
    ) {
        this.reservationId = reservationId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
        this.status = status;
        this.bookingDate = bookingDate;
        this.hotelName = hotelName;
        this.hotelCity = hotelCity;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.roomPricePerNight = roomPricePerNight;
        this.guests = guests;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getRoomPricePerNight() {
        return roomPricePerNight;
    }

    public void setRoomPricePerNight(double roomPricePerNight) {
        this.roomPricePerNight = roomPricePerNight;
    }

    public List<GuestDTO> getGuests() {
        return guests;
    }

    public void setGuests(List<GuestDTO> guests) {
        this.guests = guests;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "reservationId=" + reservationId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", bookingDate=" + bookingDate +
                ", hotelName='" + hotelName + '\'' +
                ", hotelCity='" + hotelCity + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomPricePerNight=" + roomPricePerNight +
                ", guests=" + guests +
                '}';
    }
}
