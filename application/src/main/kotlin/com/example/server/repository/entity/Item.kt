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

@Entity
@SequenceGenerator(name = "items_id_seq", allocationSize = 1)
@Table(name = "items")
class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_id_seq")
    var id: Long? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "is_available")
    var available: Boolean? = null

    @JoinColumn(name = "owner_id")
    @ManyToOne
    var owner: User? = null

    @JoinColumn(name = "request_id")
    @ManyToOne
    var request: ItemRequest? = null
}
