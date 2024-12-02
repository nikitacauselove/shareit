package com.example.server.user;

import com.example.server.exception.ConflictException;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.UserRepository;
import com.example.server.service.UserService;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
public class UserServiceTestWithContext {
    private final UserService userService;
    private final UserRepository userRepository;

    @Test
    public void create() {
        User user = new User(null, "user", "user@user.com");
        User createdUser = new User(1L, "user", "user@user.com");

        Assertions.assertEquals(createdUser, userService.create(user));
    }

    @Test
    public void createDuplicateEmail() {
        User user = new User(1L, "user", "user@user.com");
        User userWithSameEmail = new User(null, "user", "user@user.com");
        userRepository.save(user);

        Assertions.assertThrows(ConflictException.class, () -> userService.create(userWithSameEmail));
    }

    @Test
    public void update() {
        User user = new User(1L, "user", "user@user.com");
        User updatedUser = new User(1L, "update", "update@user.com");
        userRepository.save(user);

        Assertions.assertEquals(updatedUser, userService.update(updatedUser));
    }

    @Test
    public void findById() {
        User user = new User(1L, "updateName", "updateName@user.com");
        userRepository.save(user);

        Assertions.assertEquals(user, userService.findById(user.getId()));
    }

    @Test
    public void findAll() {
        User user = new User(1L, "updateName", "updateName@user.com");
        User secondUser = new User(2L, "user", "user@user.com");
        userRepository.save(user);
        userRepository.save(secondUser);

        Assertions.assertEquals(List.of(user, secondUser), userService.findAll());
    }

    @Test
    public void deleteById() {
        User user = new User(1L, "user", "user@user.com");
        userRepository.save(user);

        userService.deleteById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(user.getId()));
    }
}
