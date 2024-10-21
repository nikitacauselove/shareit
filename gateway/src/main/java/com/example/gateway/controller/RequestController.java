package com.example.gateway.controller;

import com.example.gateway.client.RequestClient;
import com.example.gateway.dto.ItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static com.example.gateway.exception.GlobalControllerExceptionHandler.FROM_ERROR_MESSAGE;
import static com.example.gateway.exception.GlobalControllerExceptionHandler.SIZE_ERROR_MESSAGE;
import static com.example.gateway.controller.UserController.X_SHARER_USER_ID;


@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ItemRequestDto itemRequestDto, @RequestHeader(X_SHARER_USER_ID) long requesterId) {
        return requestClient.create(itemRequestDto, requesterId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findById(@PathVariable long requestId, @RequestHeader(X_SHARER_USER_ID) long requesterId) {
        return requestClient.findById(requestId, requesterId);
    }

    @GetMapping
    public ResponseEntity<Object> findAllByRequesterId(@RequestHeader(X_SHARER_USER_ID) long requesterId) {
        return requestClient.findAllByRequesterId(requesterId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllByRequesterIdNot(@RequestHeader(X_SHARER_USER_ID) long requesterId,
                                                          @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) int from,
                                                          @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) int size) {
        return requestClient.findAllByRequesterIdNot(requesterId, from, size);
    }
}
