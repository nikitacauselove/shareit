package com.example.gateway.exception

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class FeignExceptionHandler {

    @ExceptionHandler(FeignException::class)
    fun handleException(exception: FeignException): ResponseEntity<Map<String, Any>> {
        val body = objectMapper.readValue(exception.contentUTF8(), object : TypeReference<Map<String, Any>>() {})

        return ResponseEntity(body, HttpStatus.valueOf(exception.status()))
    }

    companion object {
        val objectMapper: ObjectMapper = ObjectMapper()
    }
}
