package com.example.server.comment;

import com.example.server.booking.BookingRepository;
import com.example.server.comment.model.Comment;
import com.example.server.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    public Comment create(Comment comment) {
        if (bookingRepository.existsByBookerIdAndItemIdAndEndBefore(comment.getAuthor().getId(), comment.getItem().getId(), comment.getCreated())) {
            return commentRepository.save(comment);
        }
        throw new BadRequestException("Пользователи могут оставлять отзывы на вещь только после того, как взяли её в аренду.");
    }
}
