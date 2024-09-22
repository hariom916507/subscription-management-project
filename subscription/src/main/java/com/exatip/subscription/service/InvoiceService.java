package com.exatip.subscription.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exatip.subscription.entity.Invoice;
import com.exatip.subscription.repositoory.InvoiceRepository;

@Service
public class InvoiceService {

	@Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    public Invoice createInvoice(Invoice invoice) {
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long invoiceId, Invoice invoiceDetail) {
    	Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    	   invoice.setAmount(invoiceDetail.getAmount());
           invoice.setDueDate(invoiceDetail.getDueDate());
           invoice.setStatus(invoiceDetail.getStatus());
           invoice.setCreatedAt(invoice.getCreatedAt());
           invoice.setUpdatedAt(invoiceDetail.getUpdatedAt());
           invoice.setBillingDate(invoiceDetail.getBillingDate());
           

           return invoiceRepository.save(invoice);
       }

        public Invoice markAsDeleted(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
            .orElseThrow(() -> new RuntimeException("invoice not found"));
        invoice.setStatus(Invoice.STATUS_DELETED);
        return invoiceRepository.save(invoice);
    }
}
