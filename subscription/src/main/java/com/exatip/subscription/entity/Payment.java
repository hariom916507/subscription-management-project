package com.exatip.subscription.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
//@Table(name = "Payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	  private Long paymentId;

	    @ManyToOne
	    @JoinColumn(name = "subscription_id")
	    private Subscription subscription;

	    @ManyToOne
	    @JoinColumn(name = "usage_id")
	    private PayAsYouGoUsage usage;
	    
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;
	    
	    @ManyToOne
	    @JoinColumn(name = "customer_id")
	    private Customer customer;

	    private Double amount;
	    private String currency;
	    private LocalDateTime paymentDate;
	    private String transactionId;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    private String status;
	    private String payId;
	    public static final String STATUS_DELETED = "deleted";
	    
		
		

}
