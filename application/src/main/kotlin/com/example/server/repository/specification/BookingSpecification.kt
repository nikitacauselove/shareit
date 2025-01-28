package com.example.server.repository.specification

import com.example.api.dto.enums.BookingState
import com.example.api.dto.enums.BookingStatus
import com.example.server.repository.entity.Booking
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.User
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BookingSpecification {

    fun byBookerId(userId: Long): Specification<Booking> {
        return Specification<Booking> { root, query, criteriaBuilder ->
            criteriaBuilder.equal(root.get<User>("booker").get<Long>("id"), userId)
        }
    }

    fun byOwnerId(userId: Long): Specification<Booking> {
        return Specification<Booking> { root, query, criteriaBuilder ->
            criteriaBuilder.equal(root.get<Item>("item").get<User>("owner").get<Long>("id"), userId)
        }
    }

    fun byState(state: BookingState): Specification<Booking> {
        return Specification<Booking> { root, query, criteriaBuilder ->
            when (state) {
                BookingState.ALL -> criteriaBuilder.conjunction()
                BookingState.CURRENT -> criteriaBuilder.and(
                    criteriaBuilder.lessThan(root.get("start"), LocalDateTime.now()),
                    criteriaBuilder.greaterThan(root.get("end"), LocalDateTime.now())
                )
                BookingState.FUTURE -> criteriaBuilder.greaterThan(root.get("start"), LocalDateTime.now())
                BookingState.PAST -> criteriaBuilder.lessThan(root.get("end"), LocalDateTime.now())
                BookingState.REJECTED -> criteriaBuilder.equal(root.get<BookingStatus>("status"), BookingStatus.REJECTED)
                BookingState.WAITING -> criteriaBuilder.equal(root.get<BookingStatus>("status"), BookingStatus.WAITING)
            }
        }
    }
}
