package com.dsproject.order.clients;

import com.dsproject.order.dto.BusinessDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BusinessServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackGetBusinessById")
    public BusinessDTO getBusinessById(Long businessId) {
        return restTemplate.getForObject("http://localhost:8080/business/" + businessId, BusinessDTO.class);
    }

    public BusinessDTO fallbackGetBusinessById(Long businessId) {
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setId(businessId);
        businessDTO.setName("Unavailable");
        businessDTO.setEmail("Unavailable");
        return businessDTO;
    }
}