package org.micro.ecommerce.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.micro.ecommerce.core.exception.BusinessException;
import org.micro.ecommerce.core.kafka.OrderConfirmation;
import org.micro.ecommerce.core.kafka.OrderProducer;
import org.micro.ecommerce.customer.apiClient.CustomerClient;
import org.micro.ecommerce.order.dto.OrderRequest;
import org.micro.ecommerce.order.dto.OrderResponse;
import org.micro.ecommerce.order.mapper.OrderMapper;
import org.micro.ecommerce.order.repository.OrderRepository;
import org.micro.ecommerce.orderLine.dto.OrderLineRequest;
import org.micro.ecommerce.orderLine.service.OrderLineService;
import org.micro.ecommerce.product.apiClient.ProductClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final ProductClient productClient;
    private final OrderProducer orderProducer;

    public Integer createOrder (OrderRequest request) {
        // check the customer
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException(
                        "Cannot create order:: No customer exists with the provided ID")
                );
        // purchase the products -> product- ms
        var purchasedProducts = productClient.purchaseProducts(request.products());
        // persist order lines
        var order = orderRepository.save(orderMapper.toOrder(request));

        for(var purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity()
                    )
            );
        }
        //start payment process

        // send the order confirmation -- > notification-ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.orderRepository.findAll()
                .stream()
                .map(this.orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.orderRepository.findById(id)
                .map(this.orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No order found with the provided ID: %d", id))
                );
    }
}
