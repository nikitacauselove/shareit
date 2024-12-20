package com.example.server.repository.entity;

import com.example.api.dto.enums.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
@Setter
@SequenceGenerator(name = "bookings_id_seq", allocationSize = 1)
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookings_id_seq")
    private Long id;

    @Column(name = "start_date")
    private LocalDateTime start;

    @Column(name = "end_date")
    private LocalDateTime end;

    @JoinColumn(name = "item_id")
    @ManyToOne
    private Item item;

    @JoinColumn(name = "booker_id")
    @ManyToOne
    private User booker;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
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
