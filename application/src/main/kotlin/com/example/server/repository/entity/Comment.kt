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
@SequenceGenerator(name = "comments_id_seq", allocationSize = 1)
@Table(name = "comments")
class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_id_seq")
    var id: Long? = null

    @Column(name = "text")
    var text: String? = null

    @JoinColumn(name = "item_id")
    @ManyToOne
    var item: Item? = null

    @JoinColumn(name = "author_id")
    @ManyToOne
    var author: User? = null

    @Column(name = "created")
    @CreationTimestamp
    var created: LocalDateTime? = null
}
