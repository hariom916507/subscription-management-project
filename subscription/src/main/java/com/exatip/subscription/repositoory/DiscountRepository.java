package com.exatip.subscription.repositoory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exatip.subscription.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long>{

}
