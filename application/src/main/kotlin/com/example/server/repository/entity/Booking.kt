package com.example.server.repository.entity

import com.example.api.model.BookingStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@SequenceGenerator(name = "booking_id_seq", allocationSize = 1)
@Table(name = "booking")
class Booking(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_id_seq")
    var id: Long?,

    @Column(name = "start_date")
    var start: LocalDateTime,

    @Column(name = "end_date")
    var end: LocalDateTime,

    @JoinColumn(name = "item_id")
    @ManyToOne
    var item: Item,

    @JoinColumn(name = "booker_id")
    @ManyToOne
    var booker: User,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: BookingStatus
)
