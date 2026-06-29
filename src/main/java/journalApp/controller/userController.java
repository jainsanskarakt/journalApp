package journalApp.controller;

import journalApp.entity.users;
import journalApp.repository.userRepository;
import journalApp.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService;

    @Autowired
    private userRepository userRepository;

    @GetMapping
public List<users> getAllusers(){
        return userService.getAll();
    }



    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody users users){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName =authentication.getName();
        users userInDb =userService.findByuserName(userName);
        if(userInDb !=null){
            userInDb.setUserName(users.getUserName());
            userInDb.setPassword(users.getPassword());
            userService.saveNewEntry(userInDb);
        }
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
