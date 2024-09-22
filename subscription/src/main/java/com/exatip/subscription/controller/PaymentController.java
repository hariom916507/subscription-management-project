package com.exatip.subscription.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.exatip.subscription.entity.Customer;
import com.exatip.subscription.entity.Payment;
import com.exatip.subscription.entity.User;
import com.exatip.subscription.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping("/api/paymentService")
public class PaymentController {

	 // Injecting PaymentService to handle business logic
    @Autowired
    private PaymentService paymentService;
    

    // Injecting ObjectMapper to handle JSON processing
    @Autowired
    private ObjectMapper objectMapper;

    // Fetch all payments
    @GetMapping("/v1/payment")
    public List<Payment> getAllPayment() throws JsonProcessingException {
        return paymentService.getAllPayment();
    }

    // Fetch a specific payment by its ID
//    @GetMapping("/v1/payment/{paymentId}")
//    public String getPaymentById(@PathVariable Long paymentId) throws JsonProcessingException {
//        Optional<Payment> payment = paymentService.getPaymentById(paymentId);
//        return objectMapper.writeValueAsString(payment);
//    }
   @GetMapping("/v1/payment/{paymentId}")
   public Optional<Payment> getPaymentById(@PathVariable Long paymentId) throws JsonProcessingException{
	   return paymentService.getPaymentById(paymentId);
   }
    

    // Fetch payments by user ID
    @GetMapping("/v1/user/{userId}")
    public List<Payment> getPaymentByUserId(@PathVariable("userId") Long userId) {
        return paymentService.getPaymentByUserId(userId);
    }

    // Fetch payments by customer ID
    @GetMapping("/v1/customer/{customerId}")
    public List<Payment> getPaymentByCustomerId(@PathVariable("customerId") Long customerId) {
        return paymentService.getPaymentByCustomerId(customerId);
    }

    // Create a new payment record
    @PostMapping("/v1/payment")
    public Payment createPayment(@RequestBody String payment) throws JsonMappingException, JsonProcessingException {
        // Parse the incoming JSON request
        JsonNode jsonNode = objectMapper.readTree(payment);

        // Convert the JSON into a Payment object
        Payment payment2 = objectMapper.readValue(payment, Payment.class);

        // Extract the customer ID from the JSON and set it in the Payment object
        JsonNode customerNode = jsonNode.get("customer");
        JsonNode userNode = jsonNode.get("user");

        if (customerNode != null && !customerNode.isNull()) {
            Long customerId = customerNode.asLong();
            payment2.setCustomer(new Customer(customerId));
        } else {
            // Handle the case where "customer" is missing or null
            // For example, throw an exception or set a default value
            throw new IllegalArgumentException("Customer ID is required");
        }

        if (userNode != null && !userNode.isNull()) {
            Long userId = userNode.asLong();
            payment2.setUser(new User(userId));
        } else {
            // Handle the case where "user" is missing or null
            // For example, throw an exception or set a default value
            throw new IllegalArgumentException("User ID is required");
        }

        // Call the service to save the Payment object
        return paymentService.createPayment(payment2);
    }

    // Update an existing payment record
    @PutMapping("/v1/payment/{payment_id}")
    public Payment updatePayment(@PathVariable("payment_id") Long paymentId, @RequestBody String paymentDetail) throws JsonProcessingException {
        // Convert the JSON into a Payment object
        Payment payment = objectMapper.readValue(paymentDetail, Payment.class);

        // Call the service to update the Payment object
        return paymentService.updatePayment(paymentId, payment);
    }

    // Mark a payment as deleted by updating its status
    @DeleteMapping("/v1/payment{id}/status")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable Long id) {
        paymentService.markAsDeleted(id);
        return ResponseEntity.noContent().build();
    }

    
    @PostMapping("/v1/payment/createorder/{userId}/{planId}")
    public Payment createOrder(@RequestBody String subOrder, @PathVariable("userId") Long userId, @PathVariable("planId") Long planId) throws Exception {
        // Convert the JSON into a Payment object
        Payment payment = objectMapper.readValue(subOrder, Payment.class);
        System.out.println("User ID: " + userId + ", Plan ID: " + planId);

        // Call the service to create an order for payment
        return paymentService.createOrder(payment, userId, planId);
    }

    // Webhook callback for payment processing
    @PostMapping("/v1/payment/web-callback")
    public ResponseEntity<?> paymentCallbackWebhook(@RequestBody(required = false) String data) throws Exception {
        return paymentService.paymentCallbackWebhook(data);
        }
    
   
           
    
}