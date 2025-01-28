package com.example.server.service.impl

import com.example.api.dto.ItemRequestDto
import com.example.server.mapper.ItemRequestMapper
import com.example.server.repository.FromSizePageRequest.Companion.of
import com.example.server.repository.ItemRepository
import com.example.server.repository.ItemRequestRepository
import com.example.server.repository.UserRepository
import com.example.server.repository.entity.ItemRequest
import com.example.server.service.ItemRequestService
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class ItemRequestServiceImpl(
    private val itemRepository: ItemRepository,
    private val itemRequestMapper: ItemRequestMapper,
    private val itemRequestRepository: ItemRequestRepository,
    private val userRepository: UserRepository
) : ItemRequestService {

    @Transactional
    override fun create(itemRequestDto: ItemRequestDto, userId: Long): ItemRequest {
        val requester = userRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден") }

        return itemRequestRepository.save(itemRequestMapper.toItemRequest(itemRequestDto, requester))
    }

    override fun findById(id: Long): ItemRequest {
        return itemRequestRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Запрос на добавление предмета с указанным идентификатором не найден.") }
    }

    @Transactional(readOnly = true)
    override fun findByIdWithItems(id: Long, userId: Long): ItemRequestDto {
        if (!userRepository.existsById(userId)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден")
        }
        return itemRequestMapper.toItemRequestDto(findById(id), itemRepository.findAllByRequestId(id))
    }

    @Transactional(readOnly = true)
    override fun findAllByRequesterId(userId: Long): List<ItemRequestDto> {
        if (!userRepository.existsById(userId)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден")
        }
        return itemRequestMapper.toItemRequestDto(itemRequestRepository.findAllByRequesterId(userId, SORT_BY_DESCENDING_CREATED), itemRepository.findAllByRequestIdNotNull())
    }

    @Transactional(readOnly = true)
    override fun findAllByRequesterIdNot(userId: Long, from: Int, size: Int): List<ItemRequestDto> {
        if (!userRepository.existsById(userId)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден")
        }
        return itemRequestMapper.toItemRequestDto(itemRequestRepository.findAllByRequesterIdNot(userId, of(from, size, SORT_BY_DESCENDING_CREATED)), itemRepository.findAllByRequestIdNotNull())
    }

    companion object {
        private val SORT_BY_DESCENDING_CREATED = Sort.by(Sort.Direction.DESC, "created")
    }
}
