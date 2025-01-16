package com.example.server.service.impl

import com.example.api.dto.ItemRequestDto
import com.example.server.exception.NotFoundException
import com.example.server.mapper.ItemRequestMapper
import com.example.server.repository.FromSizePageRequest.Companion.of
import com.example.server.repository.ItemRepository
import com.example.server.repository.ItemRequestRepository
import com.example.server.repository.UserRepository
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.ItemRequest
import com.example.server.service.ItemRequestService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ItemRequestServiceImpl(
    private val itemRepository: ItemRepository,
    private val itemRequestRepository: ItemRequestRepository,
    private val itemRequestMapper: ItemRequestMapper,
    private val userRepository: UserRepository
) : ItemRequestService {

    override fun create(itemRequestDto: ItemRequestDto, userId: Long): ItemRequestDto {
        val requester = userRepository.findById(userId)
            .orElseThrow { NotFoundException("Пользователь с указанным идентификатором не найден") }
        val itemRequest = itemRequestMapper.toItemRequest(itemRequestDto, requester)

        return itemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest), emptyList<Item>())
    }

    override fun findById(id: Long): ItemRequest {
        return itemRequestRepository.findById(id)
            .orElseThrow { NotFoundException("Запрос на добавление вещи с указанным идентификатором не найден.") }
    }

    override fun findByIdWithItems(id: Long, userId: Long): ItemRequestDto {
        if (!userRepository.existsById(userId)) {
            throw NotFoundException("Пользователь с указанным идентификатором не найден")
        }
        return itemRequestMapper.toItemRequestDto(findById(id), itemRepository.findAllByRequestId(id))
    }

    override fun findAllByRequesterId(userId: Long): List<ItemRequestDto> {
        if (!userRepository.existsById(userId)) {
            throw NotFoundException("Пользователь с указанным идентификатором не найден")
        }
        return itemRequestMapper.toItemRequestDto(
            itemRequestRepository.findAllByRequesterId(
                userId,
                BY_CREATED_DESCENDING
            ), itemRepository.findAllByRequestIdNotNull()
        )
    }

    override fun findAllByRequesterIdNot(userId: Long, from: Int, size: Int): List<ItemRequestDto> {
        if (!userRepository.existsById(userId)) {
            throw NotFoundException("Пользователь с указанным идентификатором не найден")
        }
        return itemRequestMapper.toItemRequestDto(
            itemRequestRepository.findAllByRequesterIdNot(
                userId,
                of(from, size, BY_CREATED_DESCENDING)
            ), itemRepository.findAllByRequestIdNotNull()
        )
    }

    companion object {
        private val BY_CREATED_DESCENDING: Sort = Sort.by(Sort.Direction.DESC, "created")
    }
}
