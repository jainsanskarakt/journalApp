package journalApp.service;

import journalApp.entity.users;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import journalApp.repository.userRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import static org.mockito.ArgumentMatchers.anyString;




import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class UserDetailsServiceimplTest {
    @InjectMocks
    private UserDetailsServiceimpl userDetailsServiceimpl;


    @Mock
    private  userRepository userRepository;
    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

@Test
    void  loadUserByUsernameTest() {
    when(userRepository.findByUserName(anyString())).thenReturn(users.builder().userName("ram").password("dfgsggffd").roles(new ArrayList<>()).build());
    UserDetails User =userDetailsServiceimpl.loadUserByUsername("ram");
    Assertions.assertNotNull(User);
}


}
