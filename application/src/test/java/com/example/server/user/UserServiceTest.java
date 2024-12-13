package com.example.server.user;

import com.example.server.exception.ConflictException;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.UserRepository;
import com.example.server.service.impl.UserServiceImpl;
import com.example.server.repository.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void create() {
        User user = new User(null, "user", "user@user.com");
        User createdUser = new User(1L, "user", "user@user.com");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(createdUser);

        Assertions.assertEquals(createdUser, userService.create(user));
    }

    @Test
    public void createDuplicateEmail() {
        User user = new User(null, "user", "user@user.com");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(new DataIntegrityViolationException("could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"));

        Assertions.assertThrows(ConflictException.class, () -> userService.create(user));
    }

    @Test
    public void update() {
        User updatedUser = new User(1L, "update", "update@user.com");
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        Assertions.assertEquals(updatedUser, userService.update(updatedUser));
    }

    @Test
    public void updateEmail() {
        User existingUser = new User(1L, "updateName", "update@user.com");
        User updatedUser = new User(1L, "updateName", "updateName@user.com");
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        Assertions.assertEquals(updatedUser, userService.update(updatedUser));
    }

    @Test
    public void updateEmailExists() {
        User existingUser = new User(2L, "user", "user@user.com");
        User userWithSameEmail = new User(1L, "updateName", "user@user.com");
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(existingUser));

        Assertions.assertThrows(ConflictException.class, () -> userService.update(userWithSameEmail));
    }

    @Test
    public void findById() {
        User user = new User(1L, "updateName", "updateName@user.com");
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        Assertions.assertEquals(user, userService.findById(user.getId()));
    }

    @Test
    public void findByIdUnknown() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(100L));
    }

    @Test
    public void findAll() {
        User user = new User(1L, "updateName", "updateName@user.com");
        User secondUser = new User(2L, "user", "user@user.com");
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user, secondUser));

        Assertions.assertEquals(List.of(user, secondUser), userService.findAll());
    }

    @Test
    public void deleteById() {
        userService.deleteById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(1L));
    }
}
