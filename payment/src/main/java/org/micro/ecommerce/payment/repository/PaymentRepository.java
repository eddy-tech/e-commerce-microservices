package org.micro.ecommerce.payment.repository;

import org.micro.ecommerce.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}