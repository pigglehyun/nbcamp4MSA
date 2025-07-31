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
            throw new IllegalAccessException("이미 가입된 이메일");
        }
        String encodedPassword = passwordEncoder.encode(requestDTO.password());
        User user = new User(requestDTO.email(), requestDTO.username(), encodedPassword);
        userRepository.save(user);
    }

    private User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("유저 없음"));
    }


}


//1. 아까 말씀하신 도메인별로 DB가 나눠져있어서 서로서로 호출한다고 하셨는데 FeignClient 말씀하신게 맞는지
//2. Order->Product를 사용하면 이제 실제 product에 있는 메서드가 호출되는데
//그 메서드에서 response 이랑 똑같이 반환.
//그럼 만약에 Product 객체를 반환하는 메서드를 호출하면 Order서 어떻게 사용?.. Product 엔티티가 없는데.