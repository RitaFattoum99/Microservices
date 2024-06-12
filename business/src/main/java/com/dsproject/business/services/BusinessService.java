package com.dsproject.business.services;

import com.dsproject.business.entities.Business;
import com.dsproject.business.repositories.BusinessRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    public Business getBusinessById(Long id) {
        return businessRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Business not found"));
    }
    public Business addBusiness(Business business) {
        return businessRepository.save(business);
    }
}
