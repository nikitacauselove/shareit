package com.example.server.mapper

import com.example.api.model.UserDto
import com.example.server.repository.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(componentModel = "spring")
interface UserMapper {

    @Mapping(target = "id", ignore = true)
    fun toUser(userDto: UserDto): User

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateUser(userDto: UserDto, @MappingTarget user: User): User

    fun toUserDto(user: User): UserDto

    fun toUserDto(userList: List<User>): List<UserDto>
}
