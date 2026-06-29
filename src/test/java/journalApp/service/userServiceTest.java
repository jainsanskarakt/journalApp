package journalApp.service;

import journalApp.entity.users;
import journalApp.repository.userRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class userServiceTest {

    @Autowired
    private userRepository userRepository;


    @ParameterizedTest
    @CsvSource({
            "sanskar",
            "naman",
            "shrey",
            "vipul",
            "akshaj"
    })
    public  void testBYUserName( String name){
       users user = userRepository.findByUserName(name);
       assertTrue(!user.getUserName().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
public void test (int a , int b , int expected){
        assertEquals(expected,a+b);
    }


}
