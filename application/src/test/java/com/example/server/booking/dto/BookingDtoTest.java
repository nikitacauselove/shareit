package com.example.server.booking.dto;

import com.example.server.repository.entity.BookingStatus;
import com.example.server.dto.BookingDto;
import com.example.server.dto.ItemDto;
import com.example.server.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@JsonTest
public class BookingDtoTest {
    @Autowired
    private JacksonTester<BookingDto> jacksonTester;

    @Test
    public void serialize() throws IOException {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        ItemDto itemDto = new ItemDto(2L, "Отвертка", "Аккумуляторная отвертка", true, null);
        UserDto userDto = new UserDto(1L, "updateName", "updateName@user.com");
        BookingDto bookingDto = new BookingDto(1L, time.plusSeconds(1), time.plusSeconds(2), itemDto, userDto, BookingStatus.WAITING);
        JsonContent<BookingDto> jsonContent = jacksonTester.write(bookingDto);

        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("$.item.id").isEqualTo(2);
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.item.name").isEqualTo("Отвертка");
        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("$.booker.id").isEqualTo(1);
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("$.status").isEqualTo("WAITING");
    }
}
