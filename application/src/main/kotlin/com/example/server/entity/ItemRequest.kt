package com.example.server.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@SequenceGenerator(name = "request_id_seq", allocationSize = 1)
@Table(name = "request")
class ItemRequest(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_id_seq")
    var id: Long?,

    @Column(name = "description")
    var description: String,

    @JoinColumn(name = "requester_id")
    @ManyToOne
    var requester: User,

    @Column(name = "created")
    @CreationTimestamp
    var created: LocalDateTime?,

    @OneToMany(mappedBy = "request")
    var items: MutableSet<Item>
)
