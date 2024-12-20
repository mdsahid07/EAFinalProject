package edu.miu.cs.cs544.sahid.hotelbookingsystem.HelperModel;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Guest;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Payment;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Reservation;
import java.util.List;


public class ReservationPaymentRequest {
    private Reservation reservation; // Common reservation details
    private Payment payment;         // Payment details
    private List<Long> roomIds;      // List of room IDs
    private List<Guest> guests;

    public ReservationPaymentRequest() {}
    public ReservationPaymentRequest(Reservation reservation, Payment payment, List<Long> roomIds, List<Guest> guest) {
        this.reservation = reservation;
        this.payment = payment;
        this.roomIds = roomIds;
        this.guests=guest;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<Long> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<Long> roomIds) {
        this.roomIds = roomIds;
    }
    public List<Guest> getGuests() {
        return guests;
    }
    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    @Override
    public String toString() {
        return "ReservationPaymentRequest{" +
                "reservation=" + reservation +
                ", payment=" + payment +
                ", roomIds=" + roomIds +
                ", guests=" + guests +
                '}';
    }
}
