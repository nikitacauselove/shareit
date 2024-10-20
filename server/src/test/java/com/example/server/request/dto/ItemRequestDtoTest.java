package com.example.server.request.dto;

import com.example.server.TestConstants;
import com.example.server.dto.ItemRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.Collections;

@JsonTest
public class ItemRequestDtoTest {
    @Autowired
    private JacksonTester<ItemRequestDto> jacksonTester;

    @Test
    public void serialize() throws IOException {
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "Хотел бы воспользоваться щёткой для обуви", 1L, TestConstants.CURRENT_TIME, Collections.emptyList());
        JsonContent<ItemRequestDto> jsonContent = jacksonTester.write(itemRequestDto);

        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("Хотел бы воспользоваться щёткой для обуви");
        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("$.requesterId").isEqualTo(1);
    }
}
