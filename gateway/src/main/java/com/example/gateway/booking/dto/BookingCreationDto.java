package com.example.gateway.booking.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@StartBeforeEnd
public class BookingCreationDto {
    private final Long itemId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @FutureOrPresent(message = "Дата и время начала бронирования должна быть в настоящем или будущем.")
    @NotNull(message = "Дата и время начала бронирования не может быть пустой.")
    private final LocalDateTime start;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Future(message = "Дата и время конца бронирования должна быть в будущем.")
    @NotNull(message = "Дата и время конца бронирования не может быть пустой.")
    private final LocalDateTime end;
}
