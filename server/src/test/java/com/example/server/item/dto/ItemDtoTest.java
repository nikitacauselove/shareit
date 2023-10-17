package com.example.server.item.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
public class ItemDtoTest {
    @Autowired
    private JacksonTester<ItemDto> jacksonTester;

    @Test
    public void serialize() throws IOException {
        ItemDto itemDto = new ItemDto(1L, "Дрель", "Простая дрель", true, null);
        JsonContent<ItemDto> jsonContent = jacksonTester.write(itemDto);

        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.name").isEqualTo("Дрель");
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.description").isEqualTo("Простая дрель");
        Assertions.assertThat(jsonContent).extractingJsonPathBooleanValue("$.available").isEqualTo(true);
    }
}
