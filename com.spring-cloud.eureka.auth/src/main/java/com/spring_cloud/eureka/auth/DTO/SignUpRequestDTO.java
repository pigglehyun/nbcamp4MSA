package com.spring_cloud.eureka.auth.DTO;

public record SignUpRequestDTO(

        String email,

        String username,

        String password


) {
}
