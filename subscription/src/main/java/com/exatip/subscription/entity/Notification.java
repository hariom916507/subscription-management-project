package com.exatip.subscription.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
//@Table(name = "Notification")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notification_id;
	
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;

	    private String type;
	    private String message;
	    private String status;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    private int customerId;
	    
	    public static final String STATUS_DELETED = "deleted";



		
	    
}
