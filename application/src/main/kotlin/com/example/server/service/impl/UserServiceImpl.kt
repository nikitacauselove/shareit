package com.example.server.service.impl

import com.example.api.model.UserDto
import com.example.server.entity.User
import com.example.server.exception.ConflictException
import com.example.server.exception.NotFoundException
import com.example.server.mapper.updateEntity
import com.example.server.repository.UserRepository
import com.example.server.service.UserService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun create(user: User): User {
        try {
            return userRepository.save(user)
        } catch (exception: DataIntegrityViolationException) {
            throw ConflictException(UserRepository.CONFLICT)
        }
    }

    @Transactional
    override fun update(id: Long, userDto: UserDto): User {
        if (userRepository.existsByIdNotAndEmail(id, userDto.email)) {
            throw ConflictException(UserRepository.CONFLICT)
        }
        return findById(id).updateEntity(userDto)
    }

    override fun findById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException(UserRepository.NOT_FOUND) }
    }

    override fun findAll(): List<User> {
        return userRepository.findAll()
    }

    override fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }
}
