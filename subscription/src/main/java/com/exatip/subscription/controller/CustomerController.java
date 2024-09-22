package com.exatip.subscription.controller;
import java.util.List;

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
import com.exatip.subscription.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api/customerService")
public class CustomerController {

	   @Autowired
	    private CustomerService customerService;
	   
	   @Autowired
		private ObjectMapper objectMapper;

	    @GetMapping("/v1/customer")
	    public List<Customer> getAllCustomers() {
	        return customerService.getAllCustomers();
	    }

	    @GetMapping("/v1/customer/{id}")
	    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId) {
	        return customerService.getCustomerById(customerId)
	                .map(ResponseEntity::ok)
	                .orElseGet(() -> ResponseEntity.notFound().build());
	    }

	  /*  @PostMapping("/v1/customer")
	    public Customer createCustomer(@RequestBody String customer) throws JsonMappingException, JsonProcessingException {	    	
	    	Customer customer2 = objectMapper.readValue(customer,Customer.class);
	        return customerService.createCustomer(customer2);
	    }  */
	    
	    @PostMapping("/v1/customer")
	    public ResponseEntity<?> createCustomer(@RequestBody(required = false) String data) throws Exception {
	        return customerService.createCustomer(data);
	    }

	    
	    
       
	    @PutMapping("/v1/customer/{id}")
	    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId, @RequestBody String customerDetails)throws JsonMappingException, JsonProcessingException {
	    	Customer customer2 = objectMapper.readValue(customerDetails, Customer.class);
	        return ResponseEntity.ok(customerService.updateCustomer(customerId, customer2));
	    }
	    
	    @DeleteMapping("/v1/customer/{id}/status")
	    public ResponseEntity<Void> deleteCustomertatus(@PathVariable Long id) {
	        customerService.markAsDeleted(id);
	
	        return ResponseEntity.noContent().build();
	    }
}