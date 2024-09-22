package com.exatip.subscription.repositoory;


import org.springframework.data.jpa.repository.JpaRepository;

import com.exatip.subscription.entity.Notification;

public interface NotificationRepository  extends JpaRepository<Notification, Long>{

}
