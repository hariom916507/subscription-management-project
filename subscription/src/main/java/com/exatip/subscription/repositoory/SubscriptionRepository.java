package com.exatip.subscription.repositoory;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exatip.subscription.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserUserId(Long userId);
    
    List<Subscription> findByCustomerCustomerId(Long customerId);
    
    
}

