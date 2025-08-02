package com.spring_cloud.eureka.order.domain.order;

import com.spring_cloud.eureka.order.ProductClient;
import com.spring_cloud.eureka.order.domain.DTO.OrderRequestDTO;
import com.spring_cloud.eureka.order.domain.DTO.OrderResponseDTO;
import com.spring_cloud.eureka.order.domain.orderLine.OrderLine;
import com.spring_cloud.eureka.order.domain.orderLine.OrderLineRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProductClient productClient;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @PostConstruct
    public void registerEventListener() {
        circuitBreakerRegistry.circuitBreaker("orderService").getEventPublisher()
                .onStateTransition(event -> log.info("#######CircuitBreaker State Transition: {}", event)) // 상태 전환 이벤트 리스너
                .onFailureRateExceeded(event -> log.info("#######CircuitBreaker Failure Rate Exceeded: {}", event)) // 실패율 초과 이벤트 리스너
                .onCallNotPermitted(event -> log.info("#######CircuitBreaker Call Not Permitted: {}", event)) // 호출 차단 이벤트 리스너
                .onError(event -> log.info("#######CircuitBreaker Error: {}", event)); // 오류 발생 이벤트 리스너
    }

    public void addOrder() {
        Order order = new Order();
        orderRepository.save(order);
    }

    @CircuitBreaker(name = "orderService", fallbackMethod = "fallBackAddOrderFailed")
    public void addOrderFailed() {
        throw new RuntimeException("order fail : 잠시 후 다시 요청해주세요.");
    }

    public void fallBackAddOrderFailed(Throwable t){
        log.info("Order Fallback Method 실행");
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

    @Cacheable(value = "Order", key = "#p0") // 첫 번째 인자
    public OrderResponseDTO getOrder(Long id) {
        Order order = findOrderById(id);
        List<Long> productList = order.getProductIds().stream().map(OrderLine::getProduct_id).toList();
        return new OrderResponseDTO(order.getOrderId(), productList);
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 주문입니다."));
    }

}
