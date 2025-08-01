package com.spring_cloud.eureka.product;

import com.spring_cloud.eureka.product.DTO.RequestDTO;
import com.spring_cloud.eureka.product.DTO.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ApiResponse<?> addProduct(@RequestBody RequestDTO requestDTO) {
        return ApiResponse.success(productService.addProduct(requestDTO));
    }

    @GetMapping("/products")
    public ApiResponse<?> getProducts(){
        return ApiResponse.success(productService.getProducts());
    }

    @GetMapping("/products/{productId}")
    public ApiResponse<?> getProduct(@PathVariable(name = "productId") Long productId){
        return ApiResponse.success(productService.getProduct(productId));
    }

}
