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

import com.exatip.subscription.entity.Discount;
import com.exatip.subscription.service.DiscountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/discountService")
public class DiscountController {
	@Autowired
    private DiscountService discountService;
	
	@Autowired
	private ObjectMapper objectMapper;

    @GetMapping("/v1/discount")
    public List<Discount> getAllDiscount() {
        return discountService.getAllDiscount();
    }

    @GetMapping("/v1/discount/{discount_id}")
    public Optional<Discount> getDiscountById(@PathVariable Long discount_id) {
        return discountService.getDiscountById(discount_id);
    }

    @PostMapping("/v1/discount")
    public Discount createDiscount(@RequestBody String discount) throws JsonMappingException, JsonProcessingException{
    	Discount discount2 = objectMapper.readValue(discount, Discount.class);
        return discountService.createDiscount(discount2);
    }

    @PutMapping("/v1/discount/{discount_id}")
    public Discount updateDiscount(@PathVariable Long discount_id, @RequestBody String discountDetails) throws JsonMappingException, JsonProcessingException {
    	Discount discount2 = objectMapper.readValue(discountDetails, Discount.class);
        return discountService.updateDiscount(discount_id, discount2);
    }

    @DeleteMapping("/v1/discount/{id}/status")
    public ResponseEntity<Void> deleteDiscountStatus(@PathVariable Long id) {
        discountService.markAsDeleted(id);
        return ResponseEntity.noContent().build();
    }
}