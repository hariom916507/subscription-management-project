package com.exatip.subscription.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exatip.subscription.entity.Plan;
import com.exatip.subscription.service.PlanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/planService")
public class PlanController {
	
	@Autowired
    private PlanService planService;
	
	@Autowired
	private ObjectMapper objectMapper;

    @GetMapping("/v1/plan")
    public List<Plan> getAllPlans() {
        return planService.getAllPlans();
    }

    @GetMapping("/v1/plan/{planId}")
    public Optional<Plan> getPlanById(@PathVariable Long planId) {
        return planService.getPlanById(planId);
                
    }

    @PostMapping("/v1/plan")
    public Plan createPlan(@RequestBody String plan)throws JsonProcessingException, JsonMappingException{
    	Plan plan2 = objectMapper.readValue(plan, Plan.class); 
        return planService.createPlan(plan2);
    }

    @PutMapping("/v1/plan/{planId}")
    public Plan updatePlan(@PathVariable Long planId, @RequestBody Plan planDetails) {
        return planService.updatePlan(planId, planDetails);
    }
}