package com.example.server.mapper

import com.example.api.dto.UserDto
import com.example.server.repository.entity.User
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toUser(userDto: UserDto): User {
        return User(
            id = null,
            name = userDto.name!!,
            email = userDto.email!!
        )
    }

    fun updateUser(userDto: UserDto, user: User): User {
        if (userDto.name != null) {
            user.name = userDto.name!!
        }
        if (userDto.email != null) {
            user.email = userDto.email!!
        }

        return user
    }

    fun toUserDto(user: User): UserDto {
        var id = 0L
        var name: String? = null
        var email: String? = null

        if (user.id != null) {
            id = user.id!!
        }
        name = user.name
        email = user.email

        val userDto = UserDto(id, name, email)

        return userDto
    }

    fun toUserDto(users: List<User>): List<UserDto> {
        val list: MutableList<UserDto> = ArrayList(users.size)
        for (user in users) {
            list.add(toUserDto(user))
        }

        return list
    }
}
