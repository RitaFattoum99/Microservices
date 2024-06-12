package com.dsproject.product.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Long ownerId; // ID of business or customer
    private String ownerType; // "BUSINESS" or "CUSTOMER"
}
