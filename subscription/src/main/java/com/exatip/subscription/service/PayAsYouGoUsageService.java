package com.exatip.subscription.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exatip.subscription.entity.PayAsYouGoUsage;
import com.exatip.subscription.repositoory.PayAsYouGoUsageRepository;

@Service
public class PayAsYouGoUsageService {
	@Autowired
    private PayAsYouGoUsageRepository payAsYouGoUsageRepository;

    public List<PayAsYouGoUsage> getAllPayAsYouGoUsage() {
        return payAsYouGoUsageRepository.findAll();
    }

    public Optional<PayAsYouGoUsage> getPayAsYouGoUsageById(Long usage_id) {
        return payAsYouGoUsageRepository.findById(usage_id);
    }

    public PayAsYouGoUsage createUsage(PayAsYouGoUsage usage) {
    	usage.setCreatedAt(LocalDateTime.now());
    	usage.setUpdatedAt(LocalDateTime.now());
        return payAsYouGoUsageRepository.save(usage);
    }

    public PayAsYouGoUsage updateUsage(Long usage_id, PayAsYouGoUsage usageDetail) {
    	PayAsYouGoUsage usage = payAsYouGoUsageRepository.findById(usage_id)
                .orElseThrow(() -> new RuntimeException("Usage not found"));
    	   usage.setUser(usageDetail.getUser());
           usage.setResourceType(usageDetail.getResourceType());
           usage.setUsageAmount(usageDetail.getUsageAmount());
           usage.setBillingCycle(usageDetail.getBillingCycle());
           usage.setBillingDate(usageDetail.getBillingDate());
           usage.setCreatedAt(usageDetail.getCreatedAt());
           usage.setUpdatedAt(usageDetail.getUpdatedAt());
           usage.setCustomerId(usageDetail.getCustomerId());
           usage.setStatus(usageDetail.getStatus());

           return payAsYouGoUsageRepository.save(usage);
       }

        public PayAsYouGoUsage markAsDeleted(Long usage_id) {
        	PayAsYouGoUsage usage = payAsYouGoUsageRepository.findById(usage_id)
            .orElseThrow(() -> new RuntimeException("Usage not found"));
        usage.setStatus(PayAsYouGoUsage.STATUS_DELETED);
        return payAsYouGoUsageRepository.save(usage);
    }
   }








