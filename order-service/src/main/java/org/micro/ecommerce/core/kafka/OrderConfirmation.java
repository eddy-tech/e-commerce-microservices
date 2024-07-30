package org.micro.ecommerce.core.kafka;

import org.micro.ecommerce.customer.dto.CustomerResponse;
import org.micro.ecommerce.order.utils.PaymentMethod;
import org.micro.ecommerce.product.dto.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference, BigDecimal totalAccount, PaymentMethod paymentMethod, CustomerResponse customer,
        List<PurchaseResponse> products
) {}
