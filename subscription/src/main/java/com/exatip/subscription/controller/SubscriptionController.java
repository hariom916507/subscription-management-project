package com.exatip.subscription.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exatip.subscription.entity.Customer;
import com.exatip.subscription.entity.Subscription;
import com.exatip.subscription.entity.User;
import com.exatip.subscription.service.SubscriptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/subscriptionService")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@GetMapping("/v1/subscription")            
    public List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/v1/subscription/{subscriptionId}")
    public Optional<Subscription> getSubscriptionById(@PathVariable Long subscriptionId) {
        return subscriptionService.getSubscriptionById(subscriptionId);               
    }
    
    //get Subscription by userId
    @GetMapping("/v1/user/{userId}")
    public List<Subscription> getSubscriptioByUserId(@PathVariable ("userId") Long userId) {
        return subscriptionService.getSubscriptionByUserId(userId);        
    }
    
    
    //get Subscription by customerId
    @GetMapping("/v1/customer/{customerId}")
    public List<Subscription> getSubscriptionByCustomerId(@PathVariable ("customerId") Long customerId) {
    	return subscriptionService.getSubscriptionByCustomerId(customerId);
    }

    @PostMapping("/v1/subscription")
 public Subscription createSubscription(@RequestBody String subscription) throws JsonMappingException, JsonProcessingException {
    	
    	JsonNode jsonNode = objectMapper.readTree(subscription);
    	
    	Subscription subscription2 = objectMapper.readValue(subscription,Subscription.class);
    	Long customer_id = jsonNode.get("customer").asLong();
    	Long user_id = jsonNode.get("user").asLong();
    	
    	subscription2.setCustomer(new Customer(customer_id));
    	subscription2.setUser(new User(user_id)); 
    	
        return subscriptionService.createSubscription(subscription2);
    }

    @PutMapping("/v1/subscription/{subscriptionId}")
    public Subscription updateSubcription(@PathVariable Long subscriptionId, @RequestBody String subscription) throws JsonProcessingException, JsonMappingException {
    	Subscription subscription2 = objectMapper.readValue(subscription, Subscription.class);
        return subscriptionService.updateSubcription(subscriptionId, subscription2);
    }
   
    
}
