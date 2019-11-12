package com.k15t.pat;

import com.k15t.pat.model.User;
import com.k15t.pat.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void persistUser(){
        User user = new User();
        user.setName("Name");
        user.setEmail("user@test.com");
        user.setAddress("address");
        user.setPassword("12345678");

        testEntityManager.persist(user);
        Assert.assertNotNull(userRepository.getOne(1L));
    }

}
