package com.spring_cloud.eureka.order.domain.order;

import com.spring_cloud.eureka.order.domain.orderLine.OrderLine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "order_tb")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> productIds;

    public void addOrderLine(OrderLine orderLine){
        this.productIds.add(orderLine);
    }

    public void deleteOrderLine(OrderLine orderLine){
        this.productIds.remove(orderLine);
    }

}
