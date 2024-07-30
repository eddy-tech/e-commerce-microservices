package org.micro.ecommerce.payment.dto;

import org.micro.ecommerce.payment.utils.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id, BigDecimal amount, PaymentMethod paymentMethod, Integer orderId, String orderReference,
        Customer customer
) {}
