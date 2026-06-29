package journalApp.repository;

import journalApp.entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface journalEntryRepository extends MongoRepository<journalEntry, ObjectId> {

}
