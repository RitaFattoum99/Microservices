package com.dsproject.product.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private Long ownerId; // ID of business or customer
    private String ownerType; // "BUSINESS" or "CUSTOMER"
}
