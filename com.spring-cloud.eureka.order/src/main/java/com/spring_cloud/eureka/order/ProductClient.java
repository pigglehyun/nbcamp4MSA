package com.spring_cloud.eureka.order;

import com.spring_cloud.eureka.order.domain.DTO.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient("product-client")
public interface ProductClient {

    @GetMapping("/products/{productId}")
    ProductResponseDTO findById(@PathVariable("productId") Long id);



}
