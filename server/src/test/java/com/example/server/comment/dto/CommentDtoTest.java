package com.example.server.comment.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDateTime;

@JsonTest
public class CommentDtoTest {
    @Autowired
    private JacksonTester<CommentDto> jacksonTester;

    @Test
    public void serialize() throws IOException {
        CommentDto commentDto = new CommentDto(1L, "Add comment from user1", "updateName", LocalDateTime.now());
        JsonContent<CommentDto> jsonContent = jacksonTester.write(commentDto);

        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.text").isEqualTo("Add comment from user1");
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.authorName").isEqualTo("updateName");
    }
}
