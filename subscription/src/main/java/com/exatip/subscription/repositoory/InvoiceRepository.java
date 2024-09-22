package com.exatip.subscription.repositoory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exatip.subscription.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

}
