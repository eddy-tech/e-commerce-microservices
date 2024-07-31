package org.micro.notification.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.micro.notification.core.kafka.order.dto.OrderConfirmation;
import org.micro.notification.core.kafka.payment.dto.PaymentConfirmation;
import org.micro.notification.notification.utils.NotificationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {
    @Id
    private String id;
    private NotificationType type;
    private ZonedDateTime notificationDate;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;
}
