package org.micro.ecommerce.orderLine.dto;

public record OrderLineRequest(
        Integer id, Integer orderId, Integer productId, double quantity
) {}
