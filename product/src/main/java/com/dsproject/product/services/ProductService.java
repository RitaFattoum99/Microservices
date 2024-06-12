package com.dsproject.product.services;

import com.dsproject.product.entities.Product;
import com.dsproject.product.repositories.ProductRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

//    @HystrixCommand(fallbackMethod = "getDefaultProduct")
//    public Product getProductById(Long productId) {
//        return restTemplate.getForObject("http://PRODUCT/product/" + productId, Product.class);
//    }
//
//    public String getDefaultProduct(String productId) {
//        return "Product information is currently unavailable. Please try again later.";
//    }

    public Product getProductById(Long id) {
//        try {
//            Thread.sleep(5000); // Simulate delay
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product) {
        Product existingProduct = getProductById(productId);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        return productRepository.save(existingProduct);
    }
}
