package com.example.server.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDateTime start;

    @Column(name = "end_date")
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "booker_id")
    private User booker;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public boolean hasSameBooker(long bookerId) {
        return getBooker().hasSameId(bookerId);
    }

    public boolean hasSameItem(Item item) {
        return getItem().getId().equals(item.getId());
    }

    public boolean hasSameOwner(long ownerId) {
        return getItem().getOwner().hasSameId(ownerId);
    }

    public boolean isApprovedOrRejected() {
        return getStatus().equals(BookingStatus.APPROVED) || getStatus().equals(BookingStatus.REJECTED);
    }
}
