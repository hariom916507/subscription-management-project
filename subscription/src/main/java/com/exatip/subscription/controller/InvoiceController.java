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

import com.exatip.subscription.entity.Invoice;
import com.exatip.subscription.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/invoiceService")
public class InvoiceController {


	@Autowired
    private InvoiceService invoiceService;
	
	@Autowired
	private ObjectMapper  objectMapper;

    @GetMapping("/v1/invoice")
    public List<Invoice> getAllInvoice() {
        return invoiceService.getAllInvoice();
    }

    @GetMapping("/v1/invoice/{invoiceId}")
    public Optional<Invoice> getInvoiceById(@PathVariable Long invoiceId) {
        return invoiceService.getInvoiceById(invoiceId);
    }

    @PostMapping("/v1/invoice")
    public 	Invoice createInvoice(@RequestBody String invoice)throws JsonMappingException, JsonProcessingException {
    	Invoice invoice2 = objectMapper.readValue(invoice, Invoice.class);
    	return invoiceService.createInvoice(invoice2);
    }

    @PutMapping("/v1/invoice/{invoiceId}")
    public Invoice updateInvoice(@PathVariable Long invoiceId, @RequestBody String invoiceDetails)throws JsonMappingException, JsonProcessingException {
        Invoice invoice2 = objectMapper.readValue(invoiceDetails, Invoice.class);
    	return invoiceService.updateInvoice(invoiceId, invoice2);
    }

    @DeleteMapping("/v1/discount/{id}/status")
    public ResponseEntity<Void> deleteInvoiceStatus(@PathVariable Long id) {
    	invoiceService.markAsDeleted(id);

        return ResponseEntity.noContent().build();
    }
}
