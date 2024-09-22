package com.exatip.subscription.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor

@Entity
//@Table(name  = "Customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	private Long customerId;
	private String customerName;	
	private String email;
	private long contact;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private String status;
	
	public static final String STATUS_DELETED = "deleted";
	
	
	
	
}
