package com.example.server.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @Column(name = "is_available")
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private ItemRequest request;

    public boolean hasSameOwner(Item item) {
        return getOwner().hasSameId(item.getOwner());
    }

    public boolean hasSameOwner(long ownerId) {
        return getOwner().hasSameId(ownerId);
    }

    public boolean isAvailable() {
        return this.available;
    }
}
