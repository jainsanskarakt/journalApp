package journalApp.service;

import journalApp.entity.journalEntry;
import journalApp.entity.users;
import journalApp.repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalEntryService {
    @Autowired
    private journalEntryRepository journalEntryRepository;
    @Autowired
    private  userService userService;
    @Transactional
    public void saveEntry(journalEntry journalEntry, String userName){
        try {
            users user = userService.findByuserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            journalEntry saved=journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch (Exception e){
            System.out.println(e);
            throw  new RuntimeException("An error occurred while saving the entry.");
        }

    }
    public void saveEntry(journalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<journalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<journalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
public boolean deleteBYId(ObjectId id, String userName){
        boolean removed;
        try {
            users user = userService.findByuserName(userName);
             removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveNewEntry(user);
                journalEntryRepository.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw  new RuntimeException("An error occurred while deleting the entry",e);
        }
        return  removed;
}
//   public  List<journalEntry> findByUserName(String userName){
//
//   }

}
