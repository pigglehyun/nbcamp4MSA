package com.spring_cloud.eureka.order.domain.orderLine;

import com.spring_cloud.eureka.order.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private Long product_id;

    public OrderLine(Order order, Long product_id) {
        this.order = order;
        this.product_id = product_id;
    }
}
