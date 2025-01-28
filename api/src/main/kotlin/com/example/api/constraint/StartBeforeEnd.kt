package com.example.api.constraint

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [StartBeforeEndValidator::class])
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class StartBeforeEnd(

    val message: String = StartBeforeEndValidator.MESSAGE,

    val groups: Array<KClass<*>> = [],

    val payload: Array<KClass<out Payload>> = []
)
