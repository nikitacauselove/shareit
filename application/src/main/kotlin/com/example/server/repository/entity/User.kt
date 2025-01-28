package com.example.server.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@SequenceGenerator(name = "users_id_seq", allocationSize = 1)
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    var id: Long?,

    @Column(name = "name")
    var name: String,

    @Column(name = "email")
    var email: String
)
