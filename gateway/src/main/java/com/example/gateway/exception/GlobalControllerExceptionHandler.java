package com.example.gateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {
    public static final String FROM_ERROR_MESSAGE = "Индекс первого элемента не может быть меньше нуля.";
    public static final String SIZE_ERROR_MESSAGE = "Количество элементов для отображения не может быть меньше одного.";

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
}
