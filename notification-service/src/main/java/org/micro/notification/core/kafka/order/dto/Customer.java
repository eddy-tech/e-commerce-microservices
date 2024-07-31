package org.micro.notification.core.kafka.order.dto;

public record Customer(
        String id, String firstname, String lastname, String email
) {}
