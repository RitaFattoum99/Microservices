package com.dsproject.business.controllers;

import com.dsproject.business.entities.Business;
import com.dsproject.business.services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @Autowired

    @GetMapping
    public String getBusinessInfo() {
        return "Business Service";
    }
    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusiness(@PathVariable Long id) {
        Business business = businessService.getBusinessById(id);
        return ResponseEntity.ok(business);
    }

    @PostMapping
    public ResponseEntity<Business> addBusiness(@RequestBody Business business) {
        Business newBusiness = businessService.addBusiness(business);
        return new ResponseEntity<>(newBusiness, HttpStatus.CREATED);
    }
}
