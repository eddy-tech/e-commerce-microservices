package org.micro.ecommerce.customer.dto;

public record CustomerResponse(
        String id, String firstName, String lastName, String email
) {}
