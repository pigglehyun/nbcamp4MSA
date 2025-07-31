package com.spring_cloud.eureka.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer supply_price;

    public Product( String name, Integer supply_price) {
        this.name = name;
        this.supply_price = supply_price;
    }
}
