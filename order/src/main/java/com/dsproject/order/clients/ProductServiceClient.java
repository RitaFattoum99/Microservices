package com.dsproject.order.clients;

import com.dsproject.order.dto.ProductDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@FeignClient(name = "PRODUCT")
public interface ProductServiceClient {
    @GetMapping("/product/{productId}")
    ProductDTO getProductById(@PathVariable("productId") Long productId);
}
//@Service
//public class ProductServiceClient {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @HystrixCommand(fallbackMethod = "fallbackGetProductById")
//    public ProductDTO getProductById(Long productId) {
//        return restTemplate.getForObject("lb://product/" + productId, ProductDTO.class);
//    }
//
//    public ProductDTO fallbackGetProductById(Long productId) {
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setId(productId);
//        productDTO.setName("Unavailable");
//        productDTO.setPrice(0.0);
//        return productDTO;
//    }
//
//
//
//
//}
