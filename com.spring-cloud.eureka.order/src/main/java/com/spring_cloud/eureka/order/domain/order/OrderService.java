package com.spring_cloud.eureka.order.domain.order;

import com.spring_cloud.eureka.order.ProductClient;
import com.spring_cloud.eureka.order.domain.DTO.OrderRequestDTO;
import com.spring_cloud.eureka.order.domain.DTO.OrderResponseDTO;
import com.spring_cloud.eureka.order.domain.orderLine.OrderLine;
import com.spring_cloud.eureka.order.domain.orderLine.OrderLineRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProductClient productClient;

    public OrderResponseDTO addOrder() {
        Order order = new Order();
        orderRepository.save(order);
        return new OrderResponseDTO(order.getOrderId(), new ArrayList<>());
    }

    public void addProduct(Long orderId, OrderRequestDTO orderRequestDTO) {
        Order order = findOrderById(orderId);
        Long productId = orderRequestDTO.productId();
        var product = productClient.findById(productId);

        //TODO : 관심사 분리 refactoring
        OrderLine orderLine = new OrderLine(order, product.productId());
        orderLineRepository.save(orderLine);

        order.addOrderLine(orderLine);

        //
    }

    public OrderResponseDTO getOrder(Long id) {
        Order order = findOrderById(id);
        List<Long> productList = order.getProductIds().stream().map(OrderLine::getProduct_id).toList();
        return new OrderResponseDTO(order.getOrderId(), productList);
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("없음"));
    }


}
