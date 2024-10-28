package com.example.server.mapper;

import com.example.server.dto.UserDto;
import com.example.server.repository.entity.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {
    public User toUser(UserDto userDto) {
        return new User(null, userDto.getName(), userDto.getEmail());
    }

    public User toUser(User user, UserDto userDto) {
        return new User(
                user.getId(),
                userDto.getName() != null ? userDto.getName() : user.getName(),
                userDto.getEmail() != null ? userDto.getEmail() : user.getEmail()
        );
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public List<UserDto> toUserDto(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
