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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor


@Entity
//@Table(name = "PayAsYouGoUsage")
public class PayAsYouGoUsage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	private Long usage_id;
	
	    @ManyToOne    
	    @JoinColumn(name = "user_id")
	    private User user;

	    private String resourceType;
	    private Double usageAmount;
	    private String billingCycle;
	    private LocalDateTime billingDate;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    private int customerId;
	    private String status;
	    
	    public static final String STATUS_DELETED = "deleted";
	    	
		

}
		
		
		
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		
		
		
		


		
		
		
		
//		public List<Payment> getPayments() {
//			return payments;
//		}
//		public void setPayments(List<Payment> payments) {
//			this.payments = payments;
//		}
//		public List<Invoice> getInvoices() {
//			return invoices;
//		}
//		public void setInvoices(List<Invoice> invoices) {
//			this.invoices = invoices;
//		}
		 
//
//		@OneToMany(mappedBy = "usage")
//	    private List<Payment> payments;
//
//	    @OneToMany(mappedBy = "usage")
//	    private List<Invoice> invoices;

	    
	 	
	 
	 	
	 
 	
	


