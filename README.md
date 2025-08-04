### 💻개발 환경

- JDK 17
- Spring Boot
- MySQL 8.0
- Redis 
- Zipkin 

- Spring Cloud 
  - 서비스 디스커버리 : Eureka
  - 게이트웨이 : Spring Cloud Gateway
  - 로드밸런싱 : Ribbon
  - 회로 차단기 : Resilience4j


</br>

### 🧩주요 기능
1. Spring Cloud Gateway를 통해 Auth/Order/Product 로드밸런싱
2. Auth 패키지에서 토큰 생성 후 Gateway에서 토큰 검증 -> sign-in/sign-up 메서드만 authorization 없이 접근 가능
3. FeignClinet를 통해 Order 패키지에서 Product 패키지의 HTTP Method 호출
4. redisCacheManager를 통해 60초 동안 DB가 아닌 Redis에서 데이터 참조

   
</br>

### ✏️링크

[NOTION](https://www.notion.so/Project-17d07e8b59d98034a4d4fb8d3902bde2)

</br>

### 🗂️ 시스템 구성도
![MSA실습구성도 (1)](https://github.com/user-attachments/assets/45933fb6-3953-4e59-b985-13865a8189ca)


