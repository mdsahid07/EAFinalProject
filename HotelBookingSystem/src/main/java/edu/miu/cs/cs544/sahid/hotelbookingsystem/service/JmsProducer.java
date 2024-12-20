package edu.miu.cs.cs544.sahid.hotelbookingsystem.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsProducer {

    private final JmsTemplate jmsTemplate;

    public JmsProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendReservationConfirmation(String queueName, String message) {
        jmsTemplate.convertAndSend(queueName, message);
        System.out.println("Message sent to "+queueName);
    }
}
