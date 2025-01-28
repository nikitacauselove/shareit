package com.example.server.service.impl

import com.example.api.dto.UserDto
import com.example.server.mapper.UserMapper
import com.example.server.repository.UserRepository
import com.example.server.repository.entity.User
import com.example.server.service.UserService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class UserServiceImpl(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository
) : UserService {

    override fun create(user: User): User {
        try {
            return userRepository.save(user)
        } catch (exception: DataIntegrityViolationException) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Пользователь с указанным адресом электронной почты уже существует")
        }
    }

    @Transactional
    override fun update(id: Long, userDto: UserDto): User {
        if (userRepository.existsByIdNotAndEmail(id, userDto.email)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Пользователь с указанным адресом электронной почты уже существует")
        }
        return userMapper.updateUser(userDto, findById(id))
    }

    override fun findById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден") }
    }

    override fun findAll(): List<User> {
        return userRepository.findAll()
    }

    override fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }
}
