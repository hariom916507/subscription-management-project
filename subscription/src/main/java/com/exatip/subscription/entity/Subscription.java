package com.exatip.subscription.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor

@Entity
//@Table(name = "Subscription")

public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@NonNull
	  private Long subscriptionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
    
    
    @ManyToOne
    @JoinColumn(name = "customer_Id")
    private Customer customer;

    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double customPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int discount;
      
    

//	@OneToMany
//    (mappedBy = "subscription")
//    private List<Payment> payments;

    @OneToMany(mappedBy = "subscription")
    private List<Invoice> invoices;

	
    


}
