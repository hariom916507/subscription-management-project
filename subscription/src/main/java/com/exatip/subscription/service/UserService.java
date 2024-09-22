package com.exatip.subscription.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exatip.subscription.entity.Customer;
import com.exatip.subscription.entity.Subscription;
import com.exatip.subscription.entity.User;
import com.exatip.subscription.repositoory.CustomerRepository;
import com.exatip.subscription.repositoory.UserRepository;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    
//    public User getUserById(User data, Long userId)throws Exception {
//    	JSONObject obj = new JSONObject();
//        User user = userRepository.getUser(userId);
//        
//        User user1 = new User();
//        user.setEmail(user1.getEmail());
//        user.setContact(user1.getContact());
//        user.setStatus(user1.getStatus());
//        user.setCustomer(data.getCustomer());
//
//    	
//    	return data;
//    }
//    
  

    public User createUser(User user) {
    	user.setCreatedDate(LocalDateTime.now());
    	user.setUpdatedDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User updateUser(Long user_id, User userDetail) {
    	User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));
    	user.setUserId(userDetail.getUserId());
    	user.setCustomer(userDetail.getCustomer()); 
    	user.setEmail(userDetail.getEmail());
    	user.setContact(userDetail.getContact());
    	user.setCreatedDate(userDetail.getCreatedDate());
    	user.setUpdatedDate(userDetail.getUpdatedDate());
    	user.setStatus(userDetail.getStatus());

           return userRepository.save(user);
       }

    public User markAsDeleted(Long user_id) {
       	User user = userRepository.findById(user_id)
         .orElseThrow(() -> new RuntimeException("user not found"));
       	user.setStatus(User.STATUS_DELETED);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
     	User user = userRepository.findUserByEmail(email).get();
       	if(user!=null) {
      		return new User(user.getUserId());
       	}
       	else {
      		throw new RuntimeException("User not found");
   	    }
    }
        
    

        //for profile data
    public Map<String, Object> getUserProfileById(User user, Long userId) throws Exception {
         User user1 = userRepository.findUserById(userId);
         if (user1 != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("email", user1.getEmail());
            response.put("contact", user1.getContact());

            // Extract only the customer name from the Customer object
            if (user1.getCustomer() != null) {
                response.put("userName", user1.getCustomer().getCustomerName());
            }
            else {
                response.put("userName", null); // In case customer is not present
            }

            return response;
            } else {
                throw new Exception("User not found");
            }
        }


}
