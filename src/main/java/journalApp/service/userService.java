package journalApp.service;

import journalApp.entity.users;
import journalApp.repository.userRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Service
public class userService {
    @Autowired
    private userRepository userRepository;

    private static  final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(userService.class);

    public boolean saveNewEntry(users users){
      try {
          users.setPassword(passwordEncoder.encode(users.getPassword()));
          users.setRoles(Arrays.asList("USER"));
          userRepository.save(users);
          logger.info("User saved successfully");

          return  true;
      }catch (Exception e){
        logger.error("hahhahhhaa");
        logger.warn("huhuhhhuhu");
        logger.debug("heeeeeee");
        return false;
      }
    }

    public void saveUser(users user){
        userRepository.save(user);
    }


    public List<users> getAll(){
        return userRepository.findAll();
    }

    public Optional<users> getById(ObjectId id){
        return userRepository.findById(id);
    }

public void deleteBYId(ObjectId id){
    userRepository.deleteById(id);
}
public  users findByuserName(String userName){
        return userRepository.findByUserName(userName);
}
public  void saveAdmin(users user){
user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(Arrays.asList("USER","ADMIN"));
    userRepository.save(user);
}


}
