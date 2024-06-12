package com.dsproject.order.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private double price;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
