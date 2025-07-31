package com.spring_cloud.eureka.product.DTO;

import com.spring_cloud.eureka.product.Product;

public record ResponseDTO(Long productId, String name, Integer price) {
    public ResponseDTO(Product product) {
        this(product.getProductId(),product.getName(), product.getSupply_price());
    }
}
