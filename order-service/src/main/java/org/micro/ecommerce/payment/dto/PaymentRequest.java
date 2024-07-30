package org.micro.ecommerce.payment.dto;

import org.micro.ecommerce.customer.dto.CustomerResponse;
import org.micro.ecommerce.order.utils.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest (
        BigDecimal amount, PaymentMethod paymentMethod, Integer orderId, String orderReference, CustomerResponse customer
) {}
