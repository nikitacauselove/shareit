package com.example.server.controller

import com.example.api.UserApi
import com.example.api.model.UserDto
import com.example.server.mapper.UserMapper
import com.example.server.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [UserApi.PATH])
class UserController(
    private val userMapper: UserMapper,
    private val userService: UserService
) : UserApi {

    override fun create(userDto: UserDto): UserDto {
        val user = userMapper.toEntity(userDto)

        return userMapper.toDto(userService.create(user))
    }

    override fun update(id: Long, userDto: UserDto): UserDto {
        return userMapper.toDto(userService.update(id, userDto))
    }

    override fun findById(id: Long): UserDto {
        return userMapper.toDto(userService.findById(id))
    }

    override fun findAll(): List<UserDto> {
        return userMapper.toDto(userService.findAll())
    }

    override fun deleteById(id: Long) {
        userService.deleteById(id)
    }
}
