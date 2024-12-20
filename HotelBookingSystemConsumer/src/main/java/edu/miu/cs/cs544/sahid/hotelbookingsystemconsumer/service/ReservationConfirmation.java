package edu.miu.cs.cs544.sahid.hotelbookingsystemconsumer.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class ReservationConfirmation {
    @JmsListener(destination = "reservationQueue")
    public void receiveMessage(String message) {
        System.out.println("Successfully Received <" + message + ">");
    }
}