package com.dsproject.order.services;

import com.dsproject.order.clients.BusinessServiceClient;
import com.dsproject.order.clients.CustomerServiceClient;
import com.dsproject.order.clients.ProductServiceClient;
import com.dsproject.order.dto.*;
import com.dsproject.order.entities.Order;
import com.dsproject.order.entities.OrderRequest;
import com.dsproject.order.repositories.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {
    private final ProductServiceClient productServiceClient;
    private final BusinessServiceClient businessServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public OrderService(ProductServiceClient productServiceClient, BusinessServiceClient businessServiceClient, CustomerServiceClient customerServiceClient, OrderRepository orderRepository) {
        this.productServiceClient = productServiceClient;
        this.businessServiceClient = businessServiceClient;
        this.customerServiceClient = customerServiceClient;
        this.orderRepository = orderRepository;
    }

    @HystrixCommand(fallbackMethod = "getProductFallback")
    public OrderResponseDTO getOrderDetails(Long id, List products) {
        // Your logic to get the order details

        ResponseEntity<ProductResponseDTO[]> response = restTemplate.getForEntity("http://localhost:8080/product/" + id, ProductResponseDTO[].class);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null)
            return null;
        List<ProductResponseDTO> productResponseDTOS = Arrays.asList(response.getBody());

        // Return order details including products
        return new OrderResponseDTO(id,products, productResponseDTOS);
    }

    public OrderResponseDTO getProductFallback(Long id, List products) {
        // Fallback logic when product service is down
        return new OrderResponseDTO(id, products, Collections.emptyList());
    }

    public List<OrderRequest> getProductsFallback(Long orderId) {
        return Collections.emptyList();
    }


    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public OrderDTO createOrder(OrderRequest orderRequest) {
        ProductDTO product = productServiceClient.getProductById(orderRequest.getProductId());
        System.out.println("buyer id:");
        System.out.println(orderRequest.getBuyerId());
        System.out.println("seller id:");
        System.out.println(orderRequest.getSellerId());
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // Validate and fetch buyer details
        if ("BUSINESS".equals(orderRequest.getBuyerType())) {
            BusinessDTO buyer = businessServiceClient.getBusinessById(orderRequest.getBuyerId());
            if (buyer == null) {
                throw new RuntimeException("Business buyer not found");
            }
        } else if ("CUSTOMER".equals(orderRequest.getBuyerType())) {
            CustomerDTO buyer = customerServiceClient.getCustomerById(orderRequest.getBuyerId());
            if (buyer == null) {
                throw new RuntimeException("Customer buyer not found");
            }
        } else {
            throw new RuntimeException("Invalid buyer type");
        }

        // Validate and fetch seller details
        if ("BUSINESS".equals(orderRequest.getSellerType())) {
            BusinessDTO seller = businessServiceClient.getBusinessById(orderRequest.getSellerId());
            if (seller == null) {
                throw new RuntimeException("Business seller not found");
            }
        } else if ("CUSTOMER".equals(orderRequest.getSellerType())) {
            CustomerDTO seller = customerServiceClient.getCustomerById(orderRequest.getSellerId());
            if (seller == null) {
                throw new RuntimeException("Customer seller not found");
            }
        } else {
            throw new RuntimeException("Invalid seller type");
        }

        // Create and save the order
        Order order = new Order();
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(Math.round(product.getPrice()));
        order.setBuyerId(orderRequest.getBuyerId());
        order.setBuyerType(orderRequest.getBuyerType());
        order.setSellerId(orderRequest.getSellerId());
        order.setSellerType(orderRequest.getSellerType());
        order.setAmount(Double.valueOf(product.getPrice()) * orderRequest.getQuantity());
        order.setAmount(Double.valueOf(product.getPrice()) * orderRequest.getQuantity());
        order.setStatus("PENDING");

        Order savedOrder = orderRepository.save(order);

        // Convert Order entity to OrderDTO
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(savedOrder.getId());
        orderDTO.setProductId(savedOrder.getProductId());
        orderDTO.setQuantity(savedOrder.getQuantity());
        orderDTO.setPrice(savedOrder.getPrice());
        orderDTO.setBuyerId(savedOrder.getBuyerId());
        orderDTO.setBuyerType(savedOrder.getBuyerType());
        orderDTO.setSellerId(savedOrder.getSellerId());
        orderDTO.setSellerType(savedOrder.getSellerType());
        orderDTO.setAmount(savedOrder.getAmount());
        orderDTO.setStatus(savedOrder.getStatus());

        return orderDTO;
    }
}
