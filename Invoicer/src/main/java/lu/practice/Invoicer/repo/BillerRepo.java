package lu.practice.Invoicer.repo;

import lu.practice.Invoicer.model.Biller;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillerRepo extends MongoRepository<Biller, String> {
    Biller findBy_id(ObjectId _id);
}
