package com.exatip.subscription.repositoory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exatip.subscription.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	List<Customer> findByCustomerId(Long customer_id);
}
