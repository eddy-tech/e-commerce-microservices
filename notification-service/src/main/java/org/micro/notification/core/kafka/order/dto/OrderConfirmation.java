package org.micro.notification.core.kafka.order.dto;

import org.micro.notification.core.kafka.payment.util.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference, BigDecimal totalAmount, PaymentMethod paymentMethod, Customer customer,
        List<Product> products
) {}
