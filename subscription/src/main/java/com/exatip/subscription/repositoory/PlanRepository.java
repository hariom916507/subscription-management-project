package com.exatip.subscription.repositoory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exatip.subscription.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long>{

}
