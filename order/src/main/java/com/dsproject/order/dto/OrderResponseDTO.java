package com.dsproject.order.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private List<ProductResponseDTO> products;

    public OrderResponseDTO(Long orderId, List<ProductResponseDTO> products) {
        this.orderId = orderId;
        this.products = products;
    }

    public OrderResponseDTO(Long orderId, List<ProductResponseDTO> products, List<ProductResponseDTO> productResponseDTOS) {
        this.orderId = orderId;
        this.products = products;
        this.products = productResponseDTOS;
    }
}
