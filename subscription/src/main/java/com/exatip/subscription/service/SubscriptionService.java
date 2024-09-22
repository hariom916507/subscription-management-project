package com.exatip.subscription.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exatip.subscription.entity.Subscription;
import com.exatip.subscription.repositoory.SubscriptionRepository;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionService {
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	 public List<Subscription> getAllSubscriptions() {
	        return subscriptionRepository.findAll();
	    }

	    public Optional<Subscription> getSubscriptionById(Long SubscriptionId) {
	        return subscriptionRepository.findById(SubscriptionId);
	    }
	    
	    //get Subscription by userId
	    public List<Subscription> getSubscriptionByUserId(Long userId) {  
	    	return subscriptionRepository.findByUserUserId(userId);
	    }
	    
	    //get Subscription by customerId
	    public List<Subscription> getSubscriptionByCustomerId(Long customerId) {    	
	    	List<Subscription> customerSubscriptions =subscriptionRepository.findByCustomerCustomerId(customerId);
	    			for(Subscription sb : customerSubscriptions) {
	    				sb.setUser(null);
	    			}
	    return customerSubscriptions;
	    }
        
	    @Transactional
	    public Subscription createSubscription(Subscription subscription) {
	    	subscription.setCreatedAt(LocalDateTime.now());
	    	subscription.setUpdatedAt(LocalDateTime.now());
	        return subscriptionRepository.save(subscription);
	    }

	    public Subscription updateSubcription(Long subscriptionId, Subscription subscription) {
	    	Subscription subscription1 = subscriptionRepository.findById(subscriptionId)
	                .orElseThrow(() -> new RuntimeException("Plan not found"));

	    	subscription1.setUser(subscription.getUser());
	    	subscription1.setPlan(subscription.getPlan());
	    	subscription1.setStatus(subscription.getStatus());
	    	subscription1.setStartDate(subscription.getStartDate());
	    	subscription1.setEndDate(subscription.getEndDate());
	    	subscription1.setCustomPrice(subscription.getCustomPrice());
	    	subscription1.setCreatedAt(subscription.getCreatedAt());
	    	subscription1.setUpdatedAt(subscription.getUpdatedAt());
	    	subscription1.setDiscount(subscription.getDiscount());
	    	subscription1.setCustomer(subscription.getCustomer());

	        return subscriptionRepository.save(subscription);
	    }

	}