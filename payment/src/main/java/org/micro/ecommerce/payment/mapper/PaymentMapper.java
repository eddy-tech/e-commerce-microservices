package org.micro.ecommerce.payment.mapper;

import org.micro.ecommerce.payment.dto.PaymentRequest;
import org.micro.ecommerce.payment.entity.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest request) {
        if (request == null) {
            return null;
        }
        return Payment.builder()
                .id(request.id())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .orderId(request.orderId())
                .build();
    }
}
