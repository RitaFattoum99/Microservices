package com.dsproject.order.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
