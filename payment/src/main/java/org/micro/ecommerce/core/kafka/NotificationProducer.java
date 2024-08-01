package org.micro.ecommerce.core.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private final KafkaTemplate<String, PaymentNotification> kafkaTemplate;
    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public void sendNotification(PaymentNotification request) {
        log.info("Sending notification with body = < {} >", request);
        Message<PaymentNotification> message = MessageBuilder
                .withPayload(request)
                .setHeader(TOPIC, topicName)
                .build();

        kafkaTemplate.send(message);
    }

}
