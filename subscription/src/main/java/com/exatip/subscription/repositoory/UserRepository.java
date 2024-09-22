package com.exatip.subscription.repositoory;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exatip.subscription.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	@Query("Select us from User us where us.email=?1")
	
	Optional<User> findUserByEmail(String email);

	User getByUserId(Long user_id);
	
//	// Custom query to select only email and contact by userId
	@Query("SELECT u FROM User u WHERE u.userId = :userId")
	User findUserById(@Param("userId") Long userId);

	
//	@Query("SELECT u.email, u.contact, u.customer.customerName FROM User u WHERE u.userId = :userId")
//	Object[] findUserProfileById(@Param("userId") Long userId);

	

	

	
}