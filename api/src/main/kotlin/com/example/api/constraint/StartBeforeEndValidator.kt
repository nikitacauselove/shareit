package com.example.api.constraint

import com.example.api.dto.BookingCreateDto
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class StartBeforeEndValidator : ConstraintValidator<StartBeforeEnd?, BookingCreateDto> {

    override fun isValid(bookingCreateDto: BookingCreateDto, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return bookingCreateDto.start.isBefore(bookingCreateDto.end)
    }

    companion object {
        const val MESSAGE: String = "дата и время начала бронирования должны быть раньше, чем дата и время окончания бронирования"
    }
}
