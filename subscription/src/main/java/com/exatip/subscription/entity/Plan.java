package com.exatip.subscription.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor

@Entity
//@Table(name = "Plan")
public class Plan {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    @lombok.NonNull
	private Long planId;
    private String name;
    private String description;
    private Double price;
    private String billingCycle;
    private Boolean isCustom;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	
	

}
