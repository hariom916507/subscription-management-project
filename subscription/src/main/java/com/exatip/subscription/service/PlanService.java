package com.exatip.subscription.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exatip.subscription.entity.Plan;
import com.exatip.subscription.repositoory.PlanRepository;

@Service
public class PlanService {
	
	@Autowired
    private PlanRepository planRepository;

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public Optional<Plan> getPlanById(Long planId) {
        return planRepository.findById(planId);
    }

    public Plan createPlan(Plan plan) {
        plan.setCreatedAt(LocalDateTime.now());
        plan.setUpdatedAt(LocalDateTime.now());
        return planRepository.save(plan);
    }

    public Plan updatePlan(Long planId, Plan planDetails) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        plan.setName(planDetails.getName());
        plan.setDescription(planDetails.getDescription());
        plan.setPrice(planDetails.getPrice());
        plan.setBillingCycle(planDetails.getBillingCycle());
        plan.setIsCustom(planDetails.getIsCustom());
        plan.setUpdatedAt(LocalDateTime.now());

        return planRepository.save(plan);
    }
    

}