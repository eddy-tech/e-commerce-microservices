package org.micro.ecommerce.payment.service;

import lombok.RequiredArgsConstructor;
import org.micro.ecommerce.core.kafka.NotificationProducer;
import org.micro.ecommerce.core.kafka.PaymentConfirmation;
import org.micro.ecommerce.payment.dto.PaymentRequest;
import org.micro.ecommerce.payment.mapper.PaymentMapper;
import org.micro.ecommerce.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment (PaymentRequest request) {
        var payment = this.paymentRepository.save(this.paymentMapper.toPayment(request));

        this.notificationProducer.sendNotification(
                new PaymentConfirmation(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );

        return payment.getId();
    }
}
