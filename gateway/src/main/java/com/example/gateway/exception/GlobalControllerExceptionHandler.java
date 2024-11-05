package com.example.gateway.exception;

import com.example.api.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Iterator;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> onConstraintViolationException(ConstraintViolationException exception) {
        Iterator<ConstraintViolation<?>> errors = exception.getConstraintViolations().iterator();

        log.warn("Сервер не понимает запрос или пытается его обработать, но не может выполнить из-за того, что какой-то его аспект неверен.", exception);
        return Map.of("error", errors.next().getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Iterator<ObjectError> errors = exception.getBindingResult().getAllErrors().iterator();

        log.warn("Сервер не понимает запрос или пытается его обработать, но не может выполнить из-за того, что какой-то его аспект неверен.", exception);
        return Map.of("error", errors.next().getDefaultMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> onBadRequestException(BadRequestException exception) {
        log.warn("Сервер не понимает запрос или пытается его обработать, но не может выполнить из-за того, что какой-то его аспект неверен.", exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    @SneakyThrows
    public ResponseEntity<Map<String, String>> handleException(FeignException e) {
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.readTree(e.contentUTF8()).get("error").asText();

        return new ResponseEntity<>(Map.of("error", message), HttpStatus.valueOf(e.status()));
    }
}
