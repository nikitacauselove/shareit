package com.example.server.service.impl;

import com.example.api.dto.UserDto;
import com.example.server.exception.ConflictException;
import com.example.server.exception.NotFoundException;
import com.example.server.mapper.UserMapper;
import com.example.server.repository.UserRepository;
import com.example.server.repository.entity.User;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictException("Пользователь с указанным адресом электронной почты уже существует");
        }
    }

    @Override
    public User update(Long id, UserDto userDto) {
        if (userRepository.existsByIdNotAndEmail(id, userDto.email())) {
            throw new ConflictException("Пользователь с указанным адресом электронной почты уже существует");
        }
        return userRepository.save(userMapper.updateUser(userDto, findById(id)));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден"));
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
