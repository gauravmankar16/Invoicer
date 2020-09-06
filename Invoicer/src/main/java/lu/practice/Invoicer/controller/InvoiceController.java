package lu.practice.Invoicer.controller;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import lu.practice.Invoicer.Services.InvoiceNoService;
import lu.practice.Invoicer.Utils.CreateHtmlCleaner;
import lu.practice.Invoicer.Utils.Filepath;
import lu.practice.Invoicer.Utils.Utils;
import lu.practice.Invoicer.Utils.enums.Filetypes;
import lu.practice.Invoicer.configuration.JwtTokenUtil;
import lu.practice.Invoicer.model.Biller.Biller;
import lu.practice.Invoicer.model.Invoice.Invoice;
import lu.practice.Invoicer.model.Invoice.InvoiceStatus;
import lu.practice.Invoicer.repo.BillerRepo;
import lu.practice.Invoicer.repo.InvoiceRepo;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
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
    public Invoice saveInvoice(@RequestBody Invoice invoice, @RequestHeader (name = "Authorization") String token) throws Exception {
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Biller biller = billerRepo.findByEmail(userName);
        invoice.setCreatedBy(biller.getId());
        invoice.setCreatedOn(Instant.now());
        invoice.setStatus(InvoiceStatus.DATA_SAVED);
        if(invoice.getDueDate() != null) {
            invoice.setDueDate(Instant.parse(invoice.getDueDate().toString()));
        }
        test(invoice);
        return invoiceRepo.save(invoice);
    }

    @GetMapping(value = "/api/testApi")
    public void test(Invoice invoice) throws Exception {
        String invoiceDetails= Utils.getResourceString("/templates/invoices/index.html")
        .replace("$payerName$",invoice.getPayerName())
        .replace("$payerEmail$", invoice.getPayerEmail())
        .replace("$invoiceNo$",invoice.getInvoiceNo())
        .replace("$dueDate$",invoice.getDueDate().toString())
        .replace("$freeTextTwo$",invoice.getFreeTextTwo());

        TagNode tagNode = new HtmlCleaner(CreateHtmlCleaner.cleanerProperties()).clean(invoiceDetails);
        PrettyXmlSerializer xmlSerializer = new PrettyXmlSerializer(CreateHtmlCleaner.cleanerProperties());
        Filepath filePath = new Filepath("temp",Filetypes.HTML);
        File file = new File(filePath.getFileUploadsDir() + filePath.getSubFolderPath());

        Boolean folderExists=false;

        if(file.exists()){
            folderExists = true;
        }
        else{
            folderExists = file.mkdirs();
        }
        if(folderExists){
            xmlSerializer.writeToFile(
                    tagNode, filePath.fullPath(), StandardCharsets.UTF_8.name()
            );
        }
        WrapperConfig wrapperConfig = new WrapperConfig(WrapperConfig.findExecutable());
        Pdf pdf = new Pdf(wrapperConfig);
        pdf.addPageFromFile(filePath.fullPath());
        pdf.saveAs("C:/Users/gaurav.mankar/Downloads/Invoice.pdf");
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
