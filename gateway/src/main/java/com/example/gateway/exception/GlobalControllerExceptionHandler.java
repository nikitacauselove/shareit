package com.example.gateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, String>> handleException(FeignException e) throws JsonProcessingException {
        String message = OBJECT_MAPPER.readTree(e.contentUTF8()).get("error").asText();

        return new ResponseEntity<>(Map.of("error", message), HttpStatus.valueOf(e.status()));
    }
}
