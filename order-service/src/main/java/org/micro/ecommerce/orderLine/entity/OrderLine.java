package org.micro.ecommerce.orderLine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.micro.ecommerce.order.entity.Order;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "customer_line")
public class OrderLine {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(name = "product_id")
    private Integer productId;
    private double quantity;
}
