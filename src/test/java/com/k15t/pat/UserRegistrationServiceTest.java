package com.k15t.pat;

import com.k15t.pat.model.User;
import com.k15t.pat.repository.UserRepository;
import com.k15t.pat.service.UserRegistrationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRegistrationServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Test
    public void newUserRegistration_thenUserCreated() {
        User user = new User();
        user.setName("Name");
        user.setEmail("user@test.com");
        user.setAddress("address");
        user.setPassword("12345678");

        User newUser = userRegistrationService.newUser(user);
        assertNotNull(userRepository.getOne(1L));
    }

    @Test
    public void newUserRegistration_thenUserIdIsNotNull() {
        User user = new User();
        user.setName("Name");
        user.setEmail("user@test.com");
        user.setAddress("address");
        user.setPassword("12345678");

        User newUser = userRegistrationService.newUser(user);
        assertNotNull(newUser.getId());
    }

}
