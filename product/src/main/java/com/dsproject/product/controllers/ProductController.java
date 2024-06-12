package com.dsproject.product.controllers;

import com.dsproject.product.entities.Product;
import com.dsproject.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String getProductInfo() {
        return "Product Service";
    }

    @GetMapping("/{productId}")
    public Product  getOrderProduct(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
//        Product product = productService.getProductById(id);
//        return ResponseEntity.ok(product);
//    }
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(productId, product);
        return ResponseEntity.ok(updatedProduct);
    }
}
