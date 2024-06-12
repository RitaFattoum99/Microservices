package com.dsproject.business.repositories;

import com.dsproject.business.entities.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {}
