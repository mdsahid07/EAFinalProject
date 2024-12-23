package edu.miu.cs.cs544.sahid.hotelbookingsystem.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private int numberOfGuests;

    private String status; // Confirmed, Cancelled

    private LocalDate bookingDate;

    @Column(nullable = false)
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "reservation_rooms",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> rooms;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guest> guests;
    @Column(unique = true, nullable = false, updatable = false)
    private String bookingNumber;

    public Reservation() {}
    public Reservation(LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests, String status, LocalDate bookingDate, double totalPrice, User user, List<Room> room, Payment payment, List<Guest> guest, String bookingNumber) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
        this.user = user;
        this.rooms = room;
        this.payment = payment;
        this.guests = guest;
        this.bookingNumber = bookingNumber;
    }

    public Long getId() {
        return id;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Room> getRoom() {
        return rooms;
    }

    public void setRoom(List<Room> room) {
        this.rooms = room;
    }
    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public List<Guest> getGuests() {
        return guests;
    }
    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }
    public String getBookingNumber() {
        return bookingNumber;
    }
    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", status='" + status + '\'' +
                ", bookingDate=" + bookingDate +
                ", totalPrice=" + totalPrice +
                ", user=" + user +
                ", room=" + rooms +
                ", payment=" + payment +
                ", guests=" + guests +
                ", bookingNumber='" + bookingNumber + '\'' +
                '}';
    }
}
