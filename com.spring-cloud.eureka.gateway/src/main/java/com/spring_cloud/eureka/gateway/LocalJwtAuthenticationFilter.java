package com.spring_cloud.eureka.gateway;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class LocalJwtAuthenticationFilter implements GlobalFilter, Ordered {
    @Value("${service.jwt.secret-key}")
    private String secretKey;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("[Filter Start]");

        try{
            String path = exchange.getRequest().getURI().getPath();
            log.info("[Filter path] {}",path);
            if (path.equals("/auth/sign-in") || path.equals("/auth/sign-up")) {
                return chain.filter(exchange);
            }

            String token = extractToken(exchange);

            if (token == null || !validateToken(token) ) {
                throw new Exception("토큰 검증 실패");
            }

            return chain.filter(exchange);

        }catch(Exception e){
            log.error("[JWT 검증 실패] {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private String extractToken(ServerWebExchange exchange) {
        log.info("extracting token...");
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        log.info("extracting fail");
        return null;
    }

    private boolean validateToken(String token){
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token);
            log.info("#####payload :: " + claimsJws.getPayload().toString());
            return true;
        }catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            return false;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            return false;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            return false;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
