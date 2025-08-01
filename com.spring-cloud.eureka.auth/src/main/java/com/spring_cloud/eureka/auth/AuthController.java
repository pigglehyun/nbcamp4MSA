package com.spring_cloud.eureka.auth;

import com.spring_cloud.eureka.auth.DTO.ResponseDTO;
import com.spring_cloud.eureka.auth.DTO.SignInRequestDTO;
import com.spring_cloud.eureka.auth.DTO.SignUpRequestDTO;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/sign-in")
    public ApiResponse<?> signIn(@RequestBody SignInRequestDTO requestDTO) throws AuthException {
        return ApiResponse.success(authService.signIn(requestDTO));
    }

    @PostMapping("/auth/sign-up")
    public ApiResponse<?> signUp(@RequestBody @Valid SignUpRequestDTO requestDTO) throws Exception {
        authService.signUp(requestDTO);
        return ApiResponse.success("회원가입 완료");
    }
}
