package com.dsproject.order.entities;

import lombok.Data;

@Data
public class OrderRequest {
    private Long productId;
    private Long quantity;
    private Long price;
    private Long buyerId;
    private String buyerType;
    private Long sellerId;
    private String sellerType;
    private double amount;
    private String status; // "PENDING", "COMPLETED", "CANCELLED"
}
