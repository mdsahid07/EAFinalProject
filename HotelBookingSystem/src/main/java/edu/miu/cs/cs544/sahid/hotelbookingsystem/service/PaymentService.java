package edu.miu.cs.cs544.sahid.hotelbookingsystem.service;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Payment;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Reservation;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.repository.PaymentRepository;
import edu.miu.cs.cs544.sahid.hotelbookingsystem.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private final PaymentRepository paymentRepository;
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Autowired
    private ReservationRepository reservationRepository;

    // Retrieve all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Retrieve a payment by ID
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
    }


    public Payment processPayment(Payment paymentDetails, double totalPrice) {
        try {
            logger.info("Processing payment...");
            Payment payment = new Payment();
            payment.setPaymentDate(LocalDateTime.now());
            payment.setAmountPaid(totalPrice);
            payment.setPaymentMethod(paymentDetails.getPaymentMethod());
            payment.setTransactionId(generateTransactionId());
            paymentRepository.save(payment);
            return payment;
        } catch (Exception e) {
            throw new RuntimeException("Payment failed");
        }
    }

    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis(); // Example transaction ID
    }

}
