package com.spring_cloud.eureka.auth;

import com.spring_cloud.eureka.auth.DTO.ResponseDTO;
import com.spring_cloud.eureka.auth.DTO.SignInRequestDTO;
import com.spring_cloud.eureka.auth.DTO.SignUpRequestDTO;
import jakarta.security.auth.message.AuthException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDTO signIn(SignInRequestDTO requestDTO) throws AuthException {
        User user = findUserByEmail(requestDTO.email());

        if (!passwordEncoder.matches(requestDTO.password(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String accessToken = tokenProvider.createAccessToken(user.getId());

        return new ResponseDTO(user.getUsername(), accessToken);
    }

    @Transactional
    public void signUp(SignUpRequestDTO requestDTO) throws Exception {
        //이미 가입된 유저라면 회원가입 거부
        if (userRepository.findUserByEmail(requestDTO.email()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일이 존재합니다.");
        }
        String encodedPassword = passwordEncoder.encode(requestDTO.password());
        User user = new User(requestDTO.email(), requestDTO.username(), encodedPassword);
        userRepository.save(user);
    }

    private User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("존재 하지 않는 사용자입니다."));
    }

}
