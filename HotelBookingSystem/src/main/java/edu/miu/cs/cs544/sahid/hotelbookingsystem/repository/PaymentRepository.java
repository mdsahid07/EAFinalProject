package edu.miu.cs.cs544.sahid.hotelbookingsystem.repository;

import edu.miu.cs.cs544.sahid.hotelbookingsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
