package com.example.server.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@SequenceGenerator(name = "item_id_seq", allocationSize = 1)
@Table(name = "item")
class Item(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_seq")
    var id: Long?,

    @Column(name = "name")
    var name: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "is_available")
    var available: Boolean,

    @JoinColumn(name = "owner_id")
    @ManyToOne
    var owner: User,

    @JoinColumn(name = "request_id")
    @ManyToOne
    var request: ItemRequest?
)
