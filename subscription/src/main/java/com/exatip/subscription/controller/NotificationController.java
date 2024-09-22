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

import com.exatip.subscription.entity.Notification;
import com.exatip.subscription.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
@RequestMapping("/api/notificationService")
public class NotificationController { 
	@Autowired
    private NotificationService notificationService;
	
	@Autowired
	private ObjectMapper objectMapper;

    @GetMapping("/v1/notification")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotification();
    }

    @GetMapping("/v1/notification/{notification_id}")
    public Optional<Notification> getNotificationById(@PathVariable Long notification_id) {
        return notificationService.getNotificationById(notification_id);
    }

    @PostMapping("/v1/notification")
    public Notification createNotification(@RequestBody String notification) throws JsonMappingException, JsonProcessingException{
    	Notification notification2 = objectMapper.readValue(notification, Notification.class);
    	return notificationService.createNotification(notification2);
    }

    @PutMapping("/v1/notification/{notification_id}")
    public Notification updateNotification(@PathVariable Long notification_id, @RequestBody String notificationDetails) throws JsonMappingException, JsonProcessingException{
    	Notification notification2 = objectMapper.readValue(notificationDetails, Notification.class);
    	return notificationService.updateNotification(notification_id, notification2);
    }

    @DeleteMapping("/v1/notification/{id}/status")
    public ResponseEntity<Void> deleteNotificationStatus(@PathVariable Long id) {
        notificationService.markAsDeleted(id);
        return ResponseEntity.noContent().build();
    }
}