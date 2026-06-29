package journalApp.controller;

import journalApp.entity.users;
import journalApp.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class publicController {
  @Autowired
    private userService  userService;
    @PostMapping("create-user")
    public void createUser(@RequestBody users users){
        userService.saveNewEntry(users);
    }

    @GetMapping("health_check")
    public  String healthChecker(){
        return  "ok";
    }


}
