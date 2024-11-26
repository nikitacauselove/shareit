package com.example.server.mapper;

import com.example.api.dto.UserDto;
import com.example.server.repository.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toUser(UserDto userDto) {
        return new User(null, userDto.name(), userDto.email());
    }

    public User toUser(User user, UserDto userDto) {
        return new User(
                user.getId(),
                userDto.name() != null ? userDto.name() : user.getName(),
                userDto.email() != null ? userDto.email() : user.getEmail()
        );
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public List<UserDto> toUserDto(List<User> users) {
        return users.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }
}
