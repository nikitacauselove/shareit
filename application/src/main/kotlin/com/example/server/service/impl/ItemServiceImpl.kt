package com.example.server.service.impl

import com.example.api.model.ItemDto
import com.example.api.model.ItemDtoWithBooking
import com.example.server.entity.Item
import com.example.server.exception.NotFoundException
import com.example.server.mapper.ItemMapper
import com.example.server.repository.BookingRepository
import com.example.server.repository.CommentRepository
import com.example.server.repository.FromSizePageRequest.Companion.of
import com.example.server.repository.ItemRepository
import com.example.server.repository.ItemRequestRepository
import com.example.server.repository.UserRepository
import com.example.server.service.ItemService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemServiceImpl(
    private val bookingRepository: BookingRepository,
    private val commentRepository: CommentRepository,
    private val itemMapper: ItemMapper,
    private val itemRepository: ItemRepository,
    private val itemRequestRepository: ItemRequestRepository,
    private val userRepository: UserRepository
) : ItemService {

    @Transactional
    override fun create(itemDto: ItemDto, userId: Long): Item {
        val owner = userRepository.findById(userId)
            .orElseThrow { NotFoundException(UserRepository.NOT_FOUND) }
        val itemRequest = if (itemDto.requestId == null) null else itemRequestRepository.findById(itemDto.requestId!!)
            .orElseThrow { NotFoundException(ItemRequestRepository.NOT_FOUND) }

        return itemRepository.save(itemMapper.toEntity(itemDto, owner, itemRequest))
    }

    @Transactional
    override fun update(id: Long, itemDto: ItemDto, userId: Long): Item {
        val item = findById(id)

        if (userId != item.owner.id) {
            throw NotFoundException("Обновлять информацию о предмете может только его владелец")
        }
        return itemMapper.updateEntity(itemDto, item)
    }

    override fun findById(id: Long): Item {
        return itemRepository.findById(id)
            .orElseThrow { NotFoundException(ItemRepository.NOT_FOUND) }
    }

    @Transactional(readOnly = true)
    override fun findByIdWithBooking(id: Long, userId: Long): ItemDtoWithBooking {
        val item = findById(id)

        if (userId != item.owner.id) {
            return itemMapper.toDtoWithBooking(item, emptyList(), commentRepository.findAllByItemId(id))
        }
        return itemMapper.toDtoWithBooking(item, bookingRepository.findAllByItemId(id), commentRepository.findAllByItemId(id))
    }

    @Transactional(readOnly = true)
    override fun findAllByOwnerId(userId: Long, from: Int, size: Int): List<ItemDtoWithBooking> {
        val pageable = of(from, size, SORT_BY_DESCENDING_ID)
        val items = itemRepository.findAllByOwnerId(userId, pageable)
        val bookings = bookingRepository.findAllByItem_Owner_Id(userId, Pageable.unpaged())
        val comments = commentRepository.findAllByOwnerId(userId)

        return itemMapper.toDtoWithBooking(items, bookings, comments)
    }

    override fun search(text: String, from: Int, size: Int): List<Item> {
        return if (text.isBlank()) emptyList() else itemRepository.search(text.lowercase(), of(from, size))
    }

    companion object {
        private val SORT_BY_DESCENDING_ID = Sort.by(Sort.Direction.ASC, "id")
    }
}
