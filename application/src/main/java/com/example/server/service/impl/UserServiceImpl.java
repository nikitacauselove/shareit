package com.example.server.service.impl;

import com.example.server.exception.ConflictException;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.UserRepository;
import com.example.server.repository.entity.User;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictException("Пользователь с указанным адресом электронной почты уже существует.");
        }
    }

    @Override
    public User update(User user) {
        Optional<User> userWithSameEmail = userRepository.findByEmail(user.getEmail());

        if (userWithSameEmail.isEmpty() || userWithSameEmail.get().hasSameId(user)) {
            return userRepository.save(user);
        }
        throw new ConflictException("Пользователь с указанным адресом электронной почты уже существует.");
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден."));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
