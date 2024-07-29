package org.micro.ecommerce.customer.dto;

import org.micro.ecommerce.customer.entity.Address;

public record CustomerResponse (
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
){ }
