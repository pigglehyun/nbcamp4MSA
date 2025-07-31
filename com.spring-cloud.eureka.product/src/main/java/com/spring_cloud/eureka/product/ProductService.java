package com.spring_cloud.eureka.product;

import com.spring_cloud.eureka.product.DTO.RequestDTO;
import com.spring_cloud.eureka.product.DTO.ResponseDTO;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ResponseDTO addProduct(RequestDTO requestDTO) {
        Product product = new Product(requestDTO.name(), requestDTO.price());
        productRepository.save(product);
        return new ResponseDTO(product.getProductId(), product.getName(), product.getSupply_price());
    }

    @Transactional
    public List<ResponseDTO> getProducts(){
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(ResponseDTO::new).toList();
    }

    public ResponseDTO getProduct(Long id){
        Product product = findProductById(id);
        return new ResponseDTO(product.getProductId(), product.getName(), product.getSupply_price());
    }

    private Product findProductById(Long id) {
        return ( productRepository.findById(id).orElseThrow(() -> new NotFoundException("없음")));

    }
}
