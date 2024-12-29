package com.example.server.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
@Setter
@SequenceGenerator(name = "users_id_seq", allocationSize = 1)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;
}
