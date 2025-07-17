package com.example.server.mapper

import com.example.api.model.UserDto
import com.example.server.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(componentModel = "spring")
interface UserMapper {

    @Mapping(target = "id", ignore = true)
    fun toEntity(userDto: UserDto): User

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateEntity(userDto: UserDto, @MappingTarget user: User): User

    fun toDto(user: User): UserDto

    fun toDto(userList: List<User>): List<UserDto>
}

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