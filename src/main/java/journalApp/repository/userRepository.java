package journalApp.repository;

import  journalApp.entity.users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RestController;


public interface userRepository extends MongoRepository<users, ObjectId> {
 users findByUserName(String userName);

  void deleteByUserName(String userName);
}
