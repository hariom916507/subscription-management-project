package com.exatip.subscription.repositoory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exatip.subscription.entity.Payment;
import com.exatip.subscription.entity.User;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
	List<Payment> findByUserUserId(Long userId);
	
	List<Payment> findByCustomerCustomerId(Long customerId);
	
	Payment findByTransactionId(String transactionId);
	 
	@Query("select u from User u where u.userId = ?1")
	User getUser(Long userId);
	
	
	
//	@Query("select p from Plan  where p.planId = ?1")
//	Plan getPlan(Long planId);


	
}
