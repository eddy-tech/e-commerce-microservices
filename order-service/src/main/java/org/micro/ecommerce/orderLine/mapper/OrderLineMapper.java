package org.micro.ecommerce.orderLine.mapper;

import org.micro.ecommerce.order.entity.Order;
import org.micro.ecommerce.orderLine.dto.OrderLineRequest;
import org.micro.ecommerce.orderLine.dto.OrderLineResponse;
import org.micro.ecommerce.orderLine.entity.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.orderId())
                .productId(request.productId())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
