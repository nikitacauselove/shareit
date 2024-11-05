package com.example.server.exception;

import com.example.api.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> onBadRequestException(BadRequestException exception) {
        log.warn("Сервер не понимает запрос или пытается его обработать, но не может выполнить из-за того, что какой-то его аспект неверен.", exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> onNotFoundException(NotFoundException exception) {
        log.warn("Сервер не может найти запрашиваемый ресурс.", exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> onConflictException(ConflictException exception) {
        log.warn("Запрос не может быть выполнен из-за конфликтного обращения к ресурсу.", exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> onThrowable(Throwable exception) {
        log.warn("У сервера возникла проблема, с которой он не справился, а потому произошёл сбой обработки запроса.", exception);
        return Map.of("error", exception.getMessage());
    }
}
