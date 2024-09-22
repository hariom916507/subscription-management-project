package com.exatip.subscription.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exatip.subscription.entity.PayAsYouGoUsage;
import com.exatip.subscription.service.PayAsYouGoUsageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/payAsYouGoUsageService")
public class PayAsYouGoUsageController {
	@Autowired
    private PayAsYouGoUsageService payAsYouGoUsageService;
	
	@Autowired
	private ObjectMapper objectMapper;

    @GetMapping("/v1/usage")
    public List<PayAsYouGoUsage> getAllPayAsYouGoUsage() {
        return payAsYouGoUsageService.getAllPayAsYouGoUsage();
    }

    @GetMapping("/v1/usage/{usage_id}")
    public Optional<PayAsYouGoUsage> getPayAsYouGoUsageById(@PathVariable Long usage_id) {
        return payAsYouGoUsageService.getPayAsYouGoUsageById(usage_id);
    }

    @PostMapping("/v1/usage")
    public PayAsYouGoUsage createUsage(@RequestBody String usage) throws JsonMappingException, JsonProcessingException {
    	PayAsYouGoUsage usage2 = objectMapper.readValue(usage, PayAsYouGoUsage.class);
    	return payAsYouGoUsageService.createUsage(usage2);
    }

    @PutMapping("/v1/usage/{usage_id}")
    public PayAsYouGoUsage updateUsage(@PathVariable Long usage_id, @RequestBody String usageDetails) throws JsonMappingException, JsonProcessingException {
    	PayAsYouGoUsage usage2 = objectMapper.readValue(usageDetails, PayAsYouGoUsage.class);
    	return payAsYouGoUsageService.updateUsage(usage_id, usage2);
    }

    @DeleteMapping("/v1/usage/{id}/status")
    public ResponseEntity<Void> deleteUsageStatus(@PathVariable Long id) {
        payAsYouGoUsageService.markAsDeleted(id);
        return ResponseEntity.noContent().build();
    }
  
}