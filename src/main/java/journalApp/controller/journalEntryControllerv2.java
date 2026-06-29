package journalApp.controller;

import com.fasterxml.jackson.databind.JsonSerializer;
import journalApp.entity.journalEntry;
import journalApp.entity.users;
import journalApp.service.journalEntryService;
import journalApp.service.userService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/_journal")
public class journalEntryControllerv2 {

    @Autowired
      private journalEntryService journalEntryService;

    @Autowired
    private userService userService;
        @GetMapping
    public ResponseEntity<?> getAllJournalEntryOfUser(){
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName =authentication.getName();
            users user =userService.findByuserName(userName);
            List<journalEntry> all = user.getJournalEntries();
            if(all !=null && !all.isEmpty()){
                 return new ResponseEntity<>(all,HttpStatus.OK);
            }
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PostMapping
    public ResponseEntity<journalEntry> createEntry(@RequestBody journalEntry myEntry){
            try {
                Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
                String userName =authentication.getName();
                journalEntryService.saveEntry(myEntry,userName);
                return new ResponseEntity<>(myEntry,HttpStatus.OK);
            }
            catch (Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);            }

        }
        @GetMapping("id/{myId}")
    public ResponseEntity<journalEntry> getEntryById(@PathVariable ObjectId myId){
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName =authentication.getName();
            users user =userService.findByuserName(userName);
     List<journalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());

            if(!collect.isEmpty()){
                Optional<journalEntry> journalEntry = journalEntryService.getById(myId);
                if(journalEntry.isPresent()){
                    return  new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }

            }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
@DeleteMapping("id/{myId}")
public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId){
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    String userName =authentication.getName();
  boolean removed =  journalEntryService.deleteBYId(myId,userName);
    if(removed) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
@PutMapping("id/{myId}")
    public ResponseEntity<?> update(@PathVariable ObjectId myId,
                                    @RequestBody journalEntry newEntry
                                    ) {
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    String userName =authentication.getName();
    users user =userService.findByuserName(userName);
    List<journalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
    if(!collect.isEmpty()){
       Optional<journalEntry> journalEntry = journalEntryService.getById(myId);
       if(journalEntry.isPresent()){
           journalEntry old = journalEntry.get();
           old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
           old.setContent(newEntry.getContent() != null && !newEntry.equals("") ? newEntry.getContent() : old.getContent());
           journalEntryService.saveEntry(old);
           return new ResponseEntity<>(old, HttpStatus.OK);
       }
    }

          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
