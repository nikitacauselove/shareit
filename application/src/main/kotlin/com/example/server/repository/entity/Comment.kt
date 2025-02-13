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
@SequenceGenerator(name = "comment_id_seq", allocationSize = 1)
@Table(name = "comment")
class Comment(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
    var id: Long?,

    @Column(name = "text")
    var text: String,

    @JoinColumn(name = "item_id")
    @ManyToOne
    var item: Item,

    @JoinColumn(name = "author_id")
    @ManyToOne
    var author: User,

    @Column(name = "created")
    @CreationTimestamp
    var created: LocalDateTime?,
)
