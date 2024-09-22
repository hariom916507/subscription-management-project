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
//@Table(name = "Discount")
public class Discount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long discount_id ;
	
	 @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;

	    @ManyToOne
	    @JoinColumn(name = "planId")
	    private Plan plan;

	    private Double discountPercentage;
	    private LocalDateTime validFrom;
	    private LocalDateTime validTo;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    private String status;
	    
		public static final String STATUS_DELETED = "deleted";
    
		
	   
}

