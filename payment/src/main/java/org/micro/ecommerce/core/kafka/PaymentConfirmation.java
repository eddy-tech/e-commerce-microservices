package org.micro.ecommerce.core.kafka;

import org.micro.ecommerce.payment.utils.PaymentMethod;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderReference, BigDecimal amount, PaymentMethod paymentMethod, String customerFirstname,
        String customerLastname, String customerEmail
) {}
