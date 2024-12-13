package com.example.server.service.impl;

import com.example.server.exception.BadRequestException;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.CommentRepository;
import com.example.server.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public Comment create(Comment comment) {
        Long bookerId = comment.getAuthor().getId();
        Long itemId = comment.getItem().getId();

        if (!bookingRepository.existsByBookerIdAndItemIdAndEndBefore(bookerId, itemId, LocalDateTime.now())) {
            throw new BadRequestException("Пользователи могут оставлять отзывы на предмет только после того, как взяли его в аренду");
        }
        return commentRepository.save(comment);
    }
}
