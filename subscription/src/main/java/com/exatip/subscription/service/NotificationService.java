package com.exatip.subscription.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exatip.subscription.entity.Notification;
import com.exatip.subscription.repositoory.NotificationRepository;

@Service
public class NotificationService {
	@Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotification() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Long notification_id) {
        return notificationRepository.findById(notification_id);
    }

    public Notification createNotification(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Long notification_id, Notification notificationDetail) {
    	Notification notification = notificationRepository.findById(notification_id)
                .orElseThrow(() -> new RuntimeException("notification not found"));
    	   notification.setUser(notificationDetail.getUser());
           notification.setType(notificationDetail.getType());
           notification.setMessage(notificationDetail.getMessage());
           notification.setStatus(notificationDetail.getStatus());
           notification.setCreatedAt(notificationDetail.getCreatedAt());
           notification.setUpdatedAt(notificationDetail.getUpdatedAt());

           return notificationRepository.save(notification);
       }

        public Notification markAsDeleted(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus(Notification.STATUS_DELETED);
        return notificationRepository.save(notification);
    }
   }








