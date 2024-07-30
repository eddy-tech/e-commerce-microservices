package org.micro.ecommerce.orderLine.service;

import lombok.RequiredArgsConstructor;
import org.micro.ecommerce.orderLine.dto.OrderLineRequest;
import org.micro.ecommerce.orderLine.dto.OrderLineResponse;
import org.micro.ecommerce.orderLine.mapper.OrderLineMapper;
import org.micro.ecommerce.orderLine.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        return orderLineRepository.save(orderLineMapper.toOrderLine(request)).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toOrderLineResponse)
                .toList();
    }
}
