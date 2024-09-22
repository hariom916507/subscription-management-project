package com.exatip.subscription.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	//@Column(name = "user_id")
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	private String email;
	private long contact;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private String status;

	
	
	public static final String STATUS_DELETED = "deleted";
	

	

	
}

