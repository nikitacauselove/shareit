package com.example.server.exception

import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onBadRequestException(exception: BadRequestException): Map<String, String?> {
        logger.warn(
            "Сервер не понимает запрос или пытается его обработать, но не может выполнить из-за того, что какой-то его аспект неверен.",
            exception
        )
        return java.util.Map.of("error", exception.message)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onNotFoundException(exception: NotFoundException): Map<String, String?> {
        logger.warn("Сервер не может найти запрашиваемый ресурс.", exception)
        return java.util.Map.of("error", exception.message)
    }

    @ExceptionHandler(ConflictException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onConflictException(exception: ConflictException): Map<String, String?> {
        logger.warn(
            "Запрос не может быть выполнен из-за конфликтного обращения к ресурсу.",
            exception
        )
        return java.util.Map.of("error", exception.message)
    }

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun onThrowable(exception: Throwable): Map<String, String?> {
        logger.warn(
            "У сервера возникла проблема, с которой он не справился, а потому произошёл сбой обработки запроса.",
            exception
        )
        return java.util.Map.of("error", exception.message)
    }

    companion object {
        private val logger = getLogger(GlobalControllerExceptionHandler::class.qualifiedName)
    }
}
