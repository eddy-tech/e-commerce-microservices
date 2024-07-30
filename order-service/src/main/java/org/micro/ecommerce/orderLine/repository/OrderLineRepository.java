package org.micro.ecommerce.orderLine.repository;

import org.micro.ecommerce.orderLine.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}