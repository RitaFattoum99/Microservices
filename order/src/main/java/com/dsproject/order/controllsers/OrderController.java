package com.dsproject.order.controllsers;
import com.dsproject.order.clients.ProductServiceClient;
import com.dsproject.order.dto.OrderDTO;
import com.dsproject.order.dto.OrderResponseDTO;
import com.dsproject.order.dto.ProductDTO;
import com.dsproject.order.dto.ProductResponseDTO;
import com.dsproject.order.entities.Order;
import com.dsproject.order.entities.OrderRequest;
import com.dsproject.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/order")  // Ensure the URL path is correct and consistent
public class OrderController {
    private final OrderService orderService;

    private  final ProductServiceClient productServiceClient;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public OrderController(OrderService orderService, ProductServiceClient productServiceClient, RestTemplate restTemplate) {
        this.orderService = orderService;
        this.productServiceClient = productServiceClient;
        this.restTemplate = restTemplate;
    }


    @GetMapping
    public String getOrderInfo() {
        return "Order Service";
    }

    @GetMapping("/order/{productId}")
    public ProductDTO getOrderProduct(@PathVariable String productId) {
        Long productIdLong = Long.parseLong(productId);
        return productServiceClient.getProductById(productIdLong);
    }


//    @GetMapping("/order")
//    public List<OrderRequest> getOrdersById(@PathVariable Long orderId) {
//        return orderService.getOrdersById(orderId);
//    }
//

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }


//    @GetMapping("/product/{id}")
//    public ResponseEntity<?> getProduct(@PathVariable Long id) {
//        ProductDTO productDTO = productServiceClient.getProductById(id);
//
//// Check if the productDTO is not null
//        if (productDTO != null) {
//            // Convert ProductDTO to ProductResponseDTO
//            ProductResponseDTO productResponseDTO = new ProductResponseDTO(
//                    productDTO.getId(),
//                    productDTO.getName(),
//                    productDTO.getPrice()
//            );
//
//            // Create a list with a single product
//            List<ProductResponseDTO> productResponseDTOS = Collections.singletonList(productResponseDTO);
//            // Return order details including the single product
//
//            return new OrderResponseDTO(id, productResponseDTOS);
//        } else {
//            // Handle the case where the product is not found
//            // You might want to return an appropriate response or throw an exception
//            // For now, let's return null
//            return null;
//        }
////        return new ResponseEntity<>(orderService.getOrderDetails(id, Collections.emptyList()), HttpStatus.OK);
//    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        // Call the ProductServiceClient to get a single product
        ProductDTO productDTO = productServiceClient.getProductById(id);

        // Check if the productDTO is not null
        if (productDTO != null) {
            // Convert ProductDTO to ProductResponseDTO
            ProductResponseDTO productResponseDTO = new ProductResponseDTO(
                    productDTO.getId(),
                    productDTO.getName(),
                    productDTO.getPrice()
            );

            // Create a list with a single product
            List<ProductResponseDTO> productResponseDTOS = Collections.singletonList(productResponseDTO);

            // Return order details including the single product
            return new ResponseEntity<>(new OrderResponseDTO(id, productResponseDTOS), HttpStatus.OK);
        } else {
            // Handle the case where the product is not found
            // For now, let's return a response with a status indicating the product is not found
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            OrderDTO order = orderService.createOrder(orderRequest);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception and return a meaningful error response
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
