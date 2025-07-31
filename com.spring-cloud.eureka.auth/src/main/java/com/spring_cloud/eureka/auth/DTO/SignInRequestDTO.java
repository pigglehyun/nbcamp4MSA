package com.spring_cloud.eureka.auth.DTO;



public record SignInRequestDTO(

        String email,
        String password

) {
}
