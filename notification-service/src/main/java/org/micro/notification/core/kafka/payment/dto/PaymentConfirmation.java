package org.micro.notification.core.kafka.payment.dto;

import org.micro.notification.core.kafka.payment.util.PaymentMethod;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderReference, BigDecimal amount, PaymentMethod paymentMethod, String customerFirstname,
        String customerLastname, String customerEmail
) {}
