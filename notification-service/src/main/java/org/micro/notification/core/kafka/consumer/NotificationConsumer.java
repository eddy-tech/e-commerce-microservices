package org.micro.notification.core.kafka.consumer;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.micro.notification.core.kafka.order.dto.OrderConfirmation;
import org.micro.notification.core.kafka.order.email.OrderEmailService;
import org.micro.notification.core.kafka.payment.dto.PaymentConfirmation;
import org.micro.notification.core.kafka.payment.email.PaymentEmailService;
import org.micro.notification.notification.entity.Notification;
import org.micro.notification.notification.repository.NotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static java.lang.String.format;
import static org.micro.notification.notification.utils.NotificationType.ORDER_CONFIRMATION;
import static org.micro.notification.notification.utils.NotificationType.PAYMENT_CONFIRMATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final PaymentEmailService paymentEmail;
    private final OrderEmailService orderEmail;

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation));
        this.notificationRepository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(ZonedDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
        this.paymentEmail.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(format("Consuming the message from order-topic Topic:: %s", orderConfirmation));
        this.notificationRepository.save(
                Notification.builder()
                        .type(ORDER_CONFIRMATION)
                        .notificationDate(ZonedDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );
        var customerName = orderConfirmation.customer().firstname() + " " + orderConfirmation.customer().lastname();
        this.orderEmail.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
