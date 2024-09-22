package com.exatip.subscription.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exatip.subscription.entity.Payment;
import com.exatip.subscription.entity.User;
import com.exatip.subscription.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/userService")
public class UserController {
	@Autowired
    private UserService userService;
	
	@Autowired
	private ObjectMapper objectMapper;

    @GetMapping("/v1/user")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/v1/user/{user_id}")
    public Optional<User> getUserById(@PathVariable Long user_id) {
        return userService.getUserById(user_id);
    }
    
//    @GetMapping("/v1/user/{user_id}")
//    public User getUserById(@RequestBody String data, @PathVariable("userId") Long userId) throws Exception {
//        User user = objectMapper.readValue(data, User.class);
//        System.out.println("User ID: " + userId);
//
//        return userService.getUserById(user, userId);
//    }
    
    
    

    @PostMapping("/v1/user")
    public User createUser(@RequestBody String user)throws JsonProcessingException, JsonMappingException {
    	User user2 = objectMapper.readValue(user, User.class);
        return userService.createUser(user2);
    }

    @PutMapping("/v1/user/{user_id}")
    public User updateUser(@PathVariable Long user_id, @RequestBody String userDetails)throws JsonProcessingException, JsonMappingException {
    	User user2 = objectMapper.readValue(userDetails, User.class);
        return userService.updateUser(user_id, user2);
    }

    @DeleteMapping("/v1/user/{id}/status")
    public ResponseEntity<Void> deleteUserStatus(@PathVariable Long id) {
        userService.markAsDeleted(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/v1/user/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        return new ResponseEntity<>(userService.findByEmail(loginRequest.getEmail()), HttpStatus.OK);
    }
    
   


    
    /// for profile details
    @PostMapping("/v1/user/profile/{userId}")
    public Map<String, Object> getUserProfile(@RequestBody String data, @PathVariable("userId") Long userId) throws Exception {
        User user = objectMapper.readValue(data, User.class);
        return userService.getUserProfileById(user, userId);
    }


     
    
}
