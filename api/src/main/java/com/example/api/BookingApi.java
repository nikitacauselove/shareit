package com.example.api;

import com.example.api.dto.BookingCreateDto;
import com.example.api.dto.BookingDto;
import com.example.api.dto.enums.BookingState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.api.UserApi.X_SHARER_USER_ID;

@Tag(name = "Запросы на бронирование", description = "Взаимодействие с запросами на бронирование")
@Validated
public interface BookingApi {

    String PATH = "v1/bookings";

    @PostMapping
    @Operation(description = "Добавление нового запроса на бронирование")
    BookingDto create(@RequestBody @Valid BookingCreateDto bookingCreateDto, @RequestHeader(X_SHARER_USER_ID) Long userId);

    @PatchMapping("/{id}")
    @Operation(description = "Подтверждение или отклонение запроса на бронирование")
    BookingDto approveOrReject(@PathVariable Long id,
                               @RequestHeader(X_SHARER_USER_ID) Long userId,
                               @Parameter(description = "Подтверждение или отклонение запроса на бронирование") @RequestParam Boolean approved);

    @GetMapping("/{id}")
    @Operation(description = "Получение информации о запросе на бронирование")
    BookingDto findById(@PathVariable Long id, @RequestHeader(X_SHARER_USER_ID) Long userId);

    @GetMapping
    @Operation(description = "Получение пользователем списка всех его запросов на бронирование")
    List<BookingDto> findAllByBookerId(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                       @Parameter(description = "Критерий поиска запросов на бронирование") @RequestParam(defaultValue = "ALL") BookingState state,
                                       @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                       @RequestParam(defaultValue = "10") @Positive Integer size);

    @GetMapping("/owner")
    @Operation(description = "Получение владельцем списка всех запросов на бронирование для всех его предметов")
    List<BookingDto> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                      @Parameter(description = "Критерий поиска запросов на бронирование") @RequestParam(defaultValue = "ALL") BookingState state,
                                      @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                      @RequestParam(defaultValue = "10") @Positive Integer size);
}
