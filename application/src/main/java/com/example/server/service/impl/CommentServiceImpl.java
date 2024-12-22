package com.example.server.service.impl;

import com.example.api.dto.CommentDto;
import com.example.server.exception.BadRequestException;
import com.example.server.exception.NotFoundException;
import com.example.server.mapper.CommentMapper;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.UserRepository;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.CommentRepository;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.User;
import com.example.server.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final BookingRepository bookingRepository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Comment create(Long itemId, CommentDto commentDto, Long userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с указанным идентификатором не найден"));
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден"));

        if (!bookingRepository.existsByBookerIdAndItemIdAndEndBefore(userId, itemId, LocalDateTime.now())) {
            throw new BadRequestException("Пользователи могут оставлять отзывы на предмет только после того, как взяли его в аренду");
        }
        return commentRepository.save(commentMapper.toComment(commentDto, item, author));
    }
}
