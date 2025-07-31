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
    public ResponseDTO addProduct(@RequestBody RequestDTO requestDTO) {
        return productService.addProduct(requestDTO);
    }

    @GetMapping("/products")
    public List<ResponseDTO> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/products/{productId}")
    public ResponseDTO getProduct(@PathVariable(name = "productId") Long productId){
        return productService.getProduct(productId);
    }

}
