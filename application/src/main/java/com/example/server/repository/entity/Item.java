package com.example.server.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@FieldNameConstants
@Getter
@NoArgsConstructor
@Setter
@SequenceGenerator(name = "items_id_seq", allocationSize = 1)
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_available")
    private Boolean available;

    @JoinColumn(name = "owner_id")
    @ManyToOne
    private User owner;

    @JoinColumn(name = "request_id")
    @ManyToOne
    private ItemRequest request;
}
