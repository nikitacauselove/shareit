package com.example.server.service.impl

import com.example.api.dto.UserDto
import com.example.server.exception.ConflictException
import com.example.server.exception.NotFoundException
import com.example.server.mapper.UserMapper
import com.example.server.repository.UserRepository
import com.example.server.repository.entity.User
import com.example.server.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Service
class UserServiceImpl(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository
) : UserService {

    override fun create(user: User): User {
        try {
            return userRepository.save(user)
        } catch (exception: DataIntegrityViolationException) {
            throw ConflictException("Пользователь с указанным адресом электронной почты уже существует")
        }
    }

    override fun update(id: Long, userDto: UserDto): User {
        if (userRepository.existsByIdNotAndEmail(id, userDto.email)) {
            throw ConflictException("Пользователь с указанным адресом электронной почты уже существует")
        }
        return userRepository.save(userMapper.updateUser(userDto, findById(id)))
    }

    override fun findById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException("Пользователь с указанным идентификатором не найден") }
    }

    override fun findAll(): List<User> {
        return userRepository.findAll()
    }

    override fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }
}
