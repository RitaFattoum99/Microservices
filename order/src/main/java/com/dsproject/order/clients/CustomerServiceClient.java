package com.dsproject.order.clients;

import com.dsproject.order.dto.CustomerDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackGetCustomerById")
    public CustomerDTO getCustomerById(Long customerId) {
        return restTemplate.getForObject("http://localhost:8080/customer/" + customerId, CustomerDTO.class);
    }

    public CustomerDTO fallbackGetCustomerById(Long customerId) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);
        customerDTO.setName("Unavailable");
        customerDTO.setEmail("unavailable@domain.com");
        return customerDTO;
    }
}
