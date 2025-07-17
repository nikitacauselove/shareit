package com.example.server.controller

import com.example.api.UserApi
import com.example.api.model.UserDto
import com.example.server.mapper.toDto
import com.example.server.mapper.toEntity
import com.example.server.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [UserApi.PATH])
class UserController(
    private val userService: UserService
) : UserApi {

    override fun create(userDto: UserDto): UserDto {
        val user = userDto.toEntity()

        return userService.create(user).toDto()
    }

    override fun update(id: Long, userDto: UserDto): UserDto {
        return userService.update(id, userDto).toDto()
    }

    override fun findById(id: Long): UserDto {
        return userService.findById(id).toDto()
    }

    override fun findAll(): List<UserDto> {
        return userService.findAll().toDto()
    }

    override fun deleteById(id: Long) {
        userService.deleteById(id)
    }
}
