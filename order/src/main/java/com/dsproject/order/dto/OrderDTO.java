package com.dsproject.order.dto;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    private Long id;
    private Long productId;
    private Long quantity;
    private Long price;
    private Long buyerId; // ID of customer or business
    private String buyerType; // "CUSTOMER" or "BUSINESS"
    private Long sellerId; // ID of customer or business
    private String sellerType; // "CUSTOMER" or "BUSINESS"
    private double amount;
    private String status; // "PENDING", "COMPLETED", "CANCELLED"
}
