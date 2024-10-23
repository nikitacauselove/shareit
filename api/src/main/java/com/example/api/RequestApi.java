package com.example.api;

import com.example.api.dto.ItemRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static com.example.api.Constant.FROM_ERROR_MESSAGE;
import static com.example.api.Constant.SIZE_ERROR_MESSAGE;
import static com.example.api.Constant.X_SHARER_USER_ID;

@RequestMapping(path = "/requests")
@Validated
public interface RequestApi {

    @PostMapping
    ResponseEntity<Object> create(@RequestBody @Valid ItemRequestDto itemRequestDto, @RequestHeader(X_SHARER_USER_ID) long requesterId);

    @GetMapping("/{requestId}")
    ResponseEntity<Object> findById(@PathVariable long requestId, @RequestHeader(X_SHARER_USER_ID) long requesterId);

    @GetMapping
    ResponseEntity<Object> findAllByRequesterId(@RequestHeader(X_SHARER_USER_ID) long requesterId);

    @GetMapping("/all")
    ResponseEntity<Object> findAllByRequesterIdNot(@RequestHeader(X_SHARER_USER_ID) long requesterId,
                                                   @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) int from,
                                                   @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) int size);
}
