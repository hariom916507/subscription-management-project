package com.exatip.subscription.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exatip.subscription.entity.Discount;
import com.exatip.subscription.repositoory.DiscountRepository;

@Service
public class DiscountService {
	@Autowired
    private DiscountRepository discountRepository;

    public List<Discount> getAllDiscount() {
        return discountRepository.findAll();
    }

    public Optional<Discount> getDiscountById(Long discount_id) {
        return discountRepository.findById(discount_id);
    }

    public Discount createDiscount(Discount discount) {
    	discount.setValidFrom(LocalDateTime.now());
    	discount.setValidTo(LocalDateTime.now());
    	discount.setCreatedAt(LocalDateTime.now());
    	discount.setUpdatedAt(LocalDateTime.now());
        return discountRepository.save(discount);
    }

    public Discount updateDiscount(Long discount_id, Discount discountDetail) {
    	Discount discount = discountRepository.findById(discount_id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));
    	discount.setUser(discountDetail.getUser());
    	discount.setPlan(discountDetail.getPlan());
    	discount.setDiscountPercentage(discountDetail.getDiscountPercentage());
    	discount.setValidFrom(discountDetail.getValidFrom());
    	discount.setValidTo(discountDetail.getValidTo());
    	discount.setCreatedAt(discountDetail.getCreatedAt());
    	discount.setUpdatedAt(discountDetail.getUpdatedAt());
    	discount.setStatus(discountDetail.getStatus());

           return discountRepository.save(discount);
       }

        public Discount markAsDeleted(Long discount_id) {
        	Discount discount = discountRepository.findById(discount_id)
            .orElseThrow(() -> new RuntimeException("discount not found"));
        	discount.setStatus(Discount.STATUS_DELETED);
        return discountRepository.save(discount);
        }
   

}
