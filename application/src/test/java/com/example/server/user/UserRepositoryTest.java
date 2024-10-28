package com.example.server.user;

import com.example.server.repository.UserRepository;
import com.example.server.repository.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmail() {
        User user = new User(1L, "updateName", "updateName@user.com");
        userRepository.save(user);

        Assertions.assertTrue(userRepository.findByEmail("updateName@user.com").isPresent());
    }
}
