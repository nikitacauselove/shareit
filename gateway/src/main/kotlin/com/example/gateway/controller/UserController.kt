package com.example.gateway.controller

import com.example.api.UserApi
import com.example.api.model.UserDto
import com.example.gateway.client.UserClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [UserApi.PATH])
class UserController(
    val userClient: UserClient
) : UserApi {

    override fun create(userDto: UserDto): UserDto {
        return userClient.create(userDto)
    }

    override fun update(id: Long, userDto: UserDto): UserDto {
        return userClient.update(id, userDto)
    }

    override fun findById(id: Long): UserDto {
        return userClient.findById(id)
    }

    override fun findAll(): List<UserDto> {
        return userClient.findAll()
    }

    override fun deleteById(id: Long) {
        userClient.deleteById(id)
    }
}
