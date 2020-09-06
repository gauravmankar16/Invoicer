package lu.practice.Invoicer.model.Invoice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Document(collection = "Invoices")
public class Invoice {
    @Id
    private String id;
    private String createdBy;
    private String payerName;
    private String payerEmail;
    private List invoiceList;
    private Instant createdOn;
    private Instant dueDate;
    private String total;
    private String invoiceNo;
    private InvoiceStatus status;
    private Integer statusCode;
    private String freeTextTwo;

    public String getFreeTextTwo() {
        return freeTextTwo;
    }

    public void setFreeTextTwo(String freeTextTwo) {
        this.freeTextTwo = freeTextTwo;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
        setStatusCode(status.getStatusCode());
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    private void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

//    public class invoiceL{
//        private String descr;
//
//        public String getDescr() {
//            return descr;
//        }
//
//        public void setDescr(String descr) {
//            this.descr = descr;
//        }
//    }

    public List getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List invoiceList) {
        this.invoiceList = invoiceList;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
