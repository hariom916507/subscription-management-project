package com.exatip.subscription.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.exatip.subscription.controller.PaymentController;
import com.exatip.subscription.entity.Customer;
import com.exatip.subscription.entity.Payment;
import com.exatip.subscription.entity.Plan;
import com.exatip.subscription.entity.Subscription;
import com.exatip.subscription.entity.User;
import com.exatip.subscription.repositoory.PaymentRepository;
import com.exatip.subscription.repositoory.PlanRepository;
import com.exatip.subscription.repositoory.SubscriptionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    
    // Constructor-based injection for PaymentRepository
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    
    @Autowired
    private PlanRepository planRepository;
     

    // Logger instance for logging events
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    // Fetch all payments from the database
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }

    // Fetch a specific payment by its ID
    public Optional<Payment> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    // Fetch payments by user ID
    public List<Payment> getPaymentByUserId(Long userId) {
        List<Payment> userPayments = paymentRepository.findByUserUserId(userId);        
        for (Payment p : userPayments) {
            p.setUser(null);
        }
        return userPayments;
    }

    // Fetch payments by customer ID
    public List<Payment> getPaymentByCustomerId(Long customerId) {
        List<Payment> customerPayments = paymentRepository.findByCustomerCustomerId(customerId);        
        for (Payment p : customerPayments) {
            p.setUser(null);
        }
        return customerPayments;
    }

    // Create a new payment entry in the database
    @Transactional
    public Payment createPayment(Payment payment) {
        // Set the creation and update timestamps
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());        
        return paymentRepository.save(payment);
    }

    // Update an existing payment entry in the database
    @Transactional
    public Payment updatePayment(Long paymentId, Payment paymentDetail) throws JsonProcessingException {
        // Fetch the existing payment by ID, throw an exception if not found
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("payment not found"));
        
        // Update the payment details with the provided data
        payment.setSubscription(paymentDetail.getSubscription());
        payment.setUsage(paymentDetail.getUsage());
        payment.setUser(paymentDetail.getUser());
        payment.setCustomer(paymentDetail.getCustomer());
        payment.setAmount(paymentDetail.getAmount());
        payment.setCurrency(paymentDetail.getCurrency());
        payment.setPaymentDate(paymentDetail.getPaymentDate());
        payment.setCreatedAt(paymentDetail.getCreatedAt());
        payment.setUpdatedAt(paymentDetail.getUpdatedAt());
        payment.setTransactionId(paymentDetail.getTransactionId());
        return paymentRepository.save(payment);
    }

    // Mark a payment as deleted by updating its status
    public Payment markAsDeleted(Long paymentId) {
        // Fetch the existing payment by ID, throw an exception if not found
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("payment not found"));      
        // Update the status to indicate deletion
        payment.setStatus(Payment.STATUS_DELETED);        
        return paymentRepository.save(payment);
    }

 
    
    
    @Transactional
    public Payment createOrder(Payment subOrder, Long userId, Long planId) throws Exception {
        // Create a JSON object to hold the order request details
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", subOrder.getAmount() * 100); // amount in paisa
        orderRequest.put("currency", subOrder.getCurrency());
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        // Initialize Razorpay client
        RazorpayClient razorpay = new RazorpayClient("rzp_test_7GIoyqE0MHyAET", "KOBAl6P5T6p3SOutB2BgLC5Y");
        User user = paymentRepository.getUser(userId);

        // Create the order on Razorpay
        Order order = razorpay.orders.create(orderRequest);

        // Set RazorPay order details to the Payment entity
        subOrder.setTransactionId(order.get("id"));
        subOrder.setStatus(order.get("status"));
        subOrder.setCreatedAt(LocalDateTime.now());
        subOrder.setUpdatedAt(LocalDateTime.now());
        subOrder.setPaymentDate(LocalDateTime.now());
        subOrder.setUser(new User(userId));
        subOrder.setCustomer(new Customer(user.getCustomer().getCustomerId()));

        // Fetch the Plan based on planId
        Plan plan = planRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan not found"));

        // Create a Subscription and assign the plan to it
        Subscription subscription = new Subscription();
       // subscription.setStatus(order.get("status"));
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plusMonths(1));  // Assuming monthly subscription
        subscription.setCustomPrice(subOrder.getAmount());
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setUpdatedAt(LocalDateTime.now());
        subscription.setUser(subOrder.getUser());
        subscription.setCustomer(subOrder.getCustomer());
        subscription.setPlan(plan);  // Store the Plan in the Subscription

        // Save the subscription and link it to the payment
        Long subscriptionId = subscriptionRepository.save(subscription).getSubscriptionId();
        subOrder.setSubscription(subscription);
        
        paymentRepository.save(subOrder);

        return subOrder;
    }

    
    
    @Transactional   
    public ResponseEntity<?> paymentCallbackWebhook(String data) throws Exception {
        JSONObject jsonObject = new JSONObject(data);

        logger.info("Hook Called");
        logger.info(data);
         
        JSONObject payload = jsonObject.getJSONObject("payload");
        JSONObject payment = payload.getJSONObject("payment");
        JSONObject entity = payment.getJSONObject("entity");
        
        String orderId = entity.getString("order_id");
        String payId = entity.getString("id");
        String status = entity.getString("status");
        String currency = entity.getString("currency");
        double amount = entity.getDouble("amount") / 100; 
        LocalDateTime paymentDate = LocalDateTime.now(); 
        
        // Log the payment details
        logger.info("Order ID: " + orderId);
        logger.info("Payment ID: " + payId);
        logger.info("Status: " + status);
        logger.info("Currency: " + currency);
        logger.info("Amount: " + amount);

        // Retrieve the payment entry from the database using the order ID
        Payment paymentEntry = paymentRepository.findByTransactionId(orderId);
        if (paymentEntry == null) {
            logger.error("Payment entry not found for Order ID: " + orderId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the payment entry with the received data
        paymentEntry.setStatus(status);
        paymentEntry.setPaymentDate(paymentDate);
        paymentEntry.setUpdatedAt(LocalDateTime.now());
        paymentEntry.setTransactionId(orderId);
        paymentEntry.setAmount(amount);
        paymentEntry.setCurrency(currency);
        paymentEntry.setPayId(payId);
        
        // Save the updated payment entry back to the database
        paymentRepository.save(paymentEntry);
        
        Subscription subscription = paymentEntry.getSubscription();
     
        // Update the subscription status based on the payment status
        subscription.setStatus(status); // Use the same status as the payment status or customize based on your logic
        subscription.setUpdatedAt(LocalDateTime.now()); // Update the 'updated at' timestamp for the subscription

        // Save the updated subscription entry
        subscriptionRepository.save(subscription);      

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
   
    
    
}



