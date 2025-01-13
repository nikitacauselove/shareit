package com.example.liquibase

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LiquibaseApplication

fun main(args: Array<String>) {
    runApplication<LiquibaseApplication>(*args)
}
