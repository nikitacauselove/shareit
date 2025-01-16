package com.example.server.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@SequenceGenerator(name = "requests_id_seq", allocationSize = 1)
@Table(name = "requests")
class ItemRequest (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requests_id_seq")
    var id: Long?,

    @Column(name = "description")
    var description: String,

    @JoinColumn(name = "requester_id")
    @ManyToOne
    var requester: User,

    @Column(name = "created")
    @CreationTimestamp
    var created: LocalDateTime?
)
