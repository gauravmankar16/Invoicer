package lu.practice.Invoicer.controller;

import lu.practice.Invoicer.Services.InvoiceNoService;
import lu.practice.Invoicer.configuration.JwtTokenUtil;
import lu.practice.Invoicer.model.Biller.Biller;
import lu.practice.Invoicer.model.Invoice.Invoice;
import lu.practice.Invoicer.repo.BillerRepo;
import lu.practice.Invoicer.repo.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
public class InvoiceController {
    @Autowired
    private BillerRepo billerRepo;

    @Autowired
    private InvoiceNoService invoiceNoService;
    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/api/saveInvoice")
    public Invoice saveInvoice(@RequestBody Invoice invoice, @RequestHeader (name = "Authorization") String token){
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Biller biller = billerRepo.findByEmail(userName);
        invoice.setCreatedBy(biller.getId());
        invoice.setCreatedOn(Instant.now());
        if(invoice.getDueDate() != null) {
            invoice.setDueDate(Instant.parse(invoice.getDueDate().toString()));
        }
        return invoiceRepo.save(invoice);
    }

    @GetMapping(value = "/api/getInvoiceNo")
    public long getInvoiceNo(@RequestHeader (name = "Authorization") String token){
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Biller biller = billerRepo.findByEmail(userName);
        long invoiceNumber = invoiceRepo.countByCreatedBy(biller.getId()) + 1;
        return invoiceNumber;
    }

    @GetMapping(value = "/api/getInvoiceData/{id}")
    public Invoice getOne(@PathVariable String id){
        return invoiceRepo.findById(id).get();
    }
}
