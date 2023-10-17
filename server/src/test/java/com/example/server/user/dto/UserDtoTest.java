package com.example.server.user.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
public class UserDtoTest {
    @Autowired
    private JacksonTester<UserDto> jacksonTester;

    @Test
    public void serialize() throws IOException {
        UserDto userDto = new UserDto(1L, "user", "user@user.com");
        JsonContent<UserDto> jsonContent = jacksonTester.write(userDto);

        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("user");
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.email").isEqualTo("user@user.com");
    }
}
