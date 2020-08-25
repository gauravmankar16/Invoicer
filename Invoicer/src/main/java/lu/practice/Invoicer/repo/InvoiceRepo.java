package lu.practice.Invoicer.repo;

import lu.practice.Invoicer.model.Biller.Biller;
import lu.practice.Invoicer.model.Invoice.Invoice;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepo extends MongoRepository<Invoice, String> {
    long countByCreatedBy(Object id);
}
