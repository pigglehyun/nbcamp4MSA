package com.spring_cloud.eureka.order.domain.order;


import com.spring_cloud.eureka.order.ApiResponse;
import com.spring_cloud.eureka.order.domain.DTO.OrderRequestDTO;
import com.spring_cloud.eureka.order.domain.DTO.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ApiResponse<?> addOrder(){
        orderService.addOrder();
        return ApiResponse.success("주문 추가 완료");

    }

    @PostMapping("/orders?fail")
    public ApiResponse<?> failOrder(){
        //TODO : 상품 API 호출 실패 케이스를 만들어서 fallback 처리 후, Response에 잠시 후에 주문 추가를 요청 해주세요. 메시지를 전달해 주세요.
        return ApiResponse.error(400,"실패");
    }

    @PutMapping("/orders/{orderId}")
    public ApiResponse<?> addProduct(@PathVariable(name = "orderId") Long orderId, @RequestBody OrderRequestDTO orderRequestDTO){
        orderService.addProduct(orderId, orderRequestDTO);
        return ApiResponse.success(orderRequestDTO);
    }

    @GetMapping("/orders/{orderId}")
    public ApiResponse<?> getOrder(@PathVariable(name = "orderId") Long orderId){
        return ApiResponse.success(orderService.getOrder(orderId));
    }
}
