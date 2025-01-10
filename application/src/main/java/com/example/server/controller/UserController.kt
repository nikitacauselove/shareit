package com.example.server.controller

import com.example.api.UserApi
import com.example.api.dto.UserDto
import com.example.server.mapper.UserMapper
import com.example.server.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [UserApi.PATH])
@RequiredArgsConstructor
class UserController(
    private val userMapper: UserMapper,
    private val userService: UserService
) : UserApi {

    override fun create(userDto: UserDto): UserDto {
        val user = userMapper.toUser(userDto)

        return userMapper.toUserDto(userService.create(user))
    }

    override fun update(id: Long, userDto: UserDto): UserDto {
        return userMapper.toUserDto(userService.update(id, userDto))
    }

    override fun findById(id: Long): UserDto {
        return userMapper.toUserDto(userService.findById(id))
    }

    override fun findAll(): List<UserDto> {
        return userMapper.toUserDto(userService.findAll())
    }

    override fun deleteById(id: Long): Map<String, String> {
        userService.deleteById(id)
        return java.util.Map.of("message", "Пользователь с указанным идентификатором был успешно удален.")
    }
}
