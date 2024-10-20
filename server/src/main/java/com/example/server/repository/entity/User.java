package com.example.server.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    public boolean hasSameId(long userId) {
        return getId().equals(userId);
    }

    public boolean hasSameId(User user) {
        return hasSameId(user.getId());
    }
}
