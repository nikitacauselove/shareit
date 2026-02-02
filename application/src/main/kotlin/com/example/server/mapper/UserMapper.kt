package com.example.server.mapper

import com.example.api.model.UserDto
import com.example.server.entity.User

fun UserDto.toEntity(): User {
    return User(id = null, name = this.name!!, email = this.email!!)
}

fun User.updateEntity(userDto: UserDto): User {
    this.name = userDto.name ?: this.name
    this.email = userDto.email ?: this.email
    return this
}

fun User.toDto(): UserDto {
    return UserDto(id = this.id!!, name = this.name, email = this.email)
}

fun List<User>.toDto(): List<UserDto> {
    return map { it.toDto() }
}