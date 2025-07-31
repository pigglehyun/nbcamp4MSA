package com.spring_cloud.eureka.order.domain.orderLine;

import com.spring_cloud.eureka.order.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine,Long> {

    OrderLine findByOrder(Order order);
}
