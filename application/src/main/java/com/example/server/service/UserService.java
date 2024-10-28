package com.example.server.service;

import com.example.server.exception.ConflictException;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.UserRepository;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictException("Пользователь с указанным адресом электронной почты уже существует.");
        }
    }

    public User update(User user) {
        Optional<User> userWithSameEmail = userRepository.findByEmail(user.getEmail());

        if (userWithSameEmail.isEmpty() || userWithSameEmail.get().hasSameId(user)) {
            return userRepository.save(user);
        }
        throw new ConflictException("Пользователь с указанным адресом электронной почты уже существует.");
    }

    @Transactional(readOnly = true)
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден."));
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
