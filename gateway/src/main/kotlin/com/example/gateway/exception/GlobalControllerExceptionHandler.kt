package com.example.gateway.exception

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(FeignException::class)
    @Throws(JsonProcessingException::class)
    fun handleException(e: FeignException): ResponseEntity<Map<String, String>> {
        val message = objectMapper.readTree(e.contentUTF8())["error"].asText()

        return ResponseEntity(java.util.Map.of("error", message), HttpStatus.valueOf(e.status()))
    }

    companion object {
        val objectMapper: ObjectMapper = ObjectMapper()
    }
}
