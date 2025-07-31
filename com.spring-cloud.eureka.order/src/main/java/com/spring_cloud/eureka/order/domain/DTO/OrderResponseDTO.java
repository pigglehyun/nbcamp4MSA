package com.spring_cloud.eureka.order.domain.DTO;

import java.util.List;

public record OrderResponseDTO(Long orderId, List<Long> productIds) {
}
