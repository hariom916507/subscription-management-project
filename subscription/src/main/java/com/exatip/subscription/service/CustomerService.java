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

import com.exatip.subscription.controller.PaymentController;
import com.exatip.subscription.entity.Customer;
import com.exatip.subscription.entity.Notification;
import com.exatip.subscription.entity.User;
import com.exatip.subscription.repositoory.CustomerRepository;
import com.exatip.subscription.repositoory.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class CustomerService {

	   @Autowired
	    private CustomerRepository customerRepository;
	   
	   @Autowired
	   private UserRepository userRepository;
	   
	   private  static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	   

	    public List<Customer> getAllCustomers() {
	        return customerRepository.findAll();
	    }

	    public Optional<Customer> getCustomerById(Long customerId) {
	        return customerRepository.findById(customerId);
	    }
 
	    
	  /*  public Customer createCustomer(Customer customer) {
	        customer.setCreatedDate(LocalDateTime.now());
	        customer.setUpdatedDate(LocalDateTime.now());
	        return customerRepository.save(customer);
	    }  */
	    @Transactional
	    public ResponseEntity<?> createCustomer(String data) throws Exception {
	        if (data == null || data.isEmpty()) {
	            return new ResponseEntity<>("No data provided", HttpStatus.BAD_REQUEST);
	        }

	        JSONObject jsonObject = new JSONObject(data);

	        // Parsing JSON data
	        String customerName = jsonObject.getString("fullName");
	        String email = jsonObject.getString("email");
	        long contact = jsonObject.getLong("phoneNumber");
	        String status = "active"; // Assuming a default status of 'active'
	        LocalDateTime createdDate = LocalDateTime.now();
	        LocalDateTime updatedDate = LocalDateTime.now();

	        // Create a new customer entity
	        Customer customer = new Customer();
	        customer.setCustomerName(customerName);
	        customer.setEmail(email);
	        customer.setContact(contact);
	        customer.setStatus(status);
	        customer.setCreatedDate(createdDate);
	        customer.setUpdatedDate(updatedDate);

	        // Save to the repository
	       Long customerId = customerRepository.save(customer).getCustomerId();

	        User user = new User();
	        user.setCustomer(new Customer(customerId));
	        user.setContact(customer.getContact());
	        user.setEmail(customer.getEmail());
	        user.setCreatedDate(LocalDateTime.now());
	        user.setUpdatedDate(LocalDateTime.now());
	        user.setStatus(customer.getStatus());
	               
	        userRepository.save(user);
	        return new ResponseEntity<>(customer, HttpStatus.CREATED);
	    }
	

	    public Customer updateCustomer(Long customerId, Customer customerDetails) {
	        return customerRepository.findById(customerId)
	                .map(customer -> {
	                    customer.setCustomerName(customerDetails.getCustomerName());
	                    customer.setEmail(customerDetails.getEmail());
	                    customer.setContact(customerDetails.getContact());
	                    customer.setUpdatedDate(LocalDateTime.now());
	                    return customerRepository.save(customer);
	                }).orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
	    }
	    public Customer markAsDeleted(Long customerId) {
	        Customer customer = customerRepository.findById(customerId)
	            .orElseThrow(() -> new RuntimeException("Customer  not found"));
	        customer.setStatus(Notification.STATUS_DELETED);
	        return customerRepository.save(customer);
	    }
}