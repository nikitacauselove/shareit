package com.example.server.comment;

import com.example.api.BadRequestException;
import com.example.server.TestConstants;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.CommentRepository;
import com.example.server.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @Test
    public void create() {
        Comment comment = new Comment(1L, "Add comment from user1", TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, TestConstants.CURRENT_TIME.plusSeconds(1));
        Mockito.when(bookingRepository.existsByBookerIdAndItemIdAndEndBefore(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(LocalDateTime.class))).thenReturn(true);
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(comment);

        Assertions.assertEquals(comment, commentService.create(comment));
    }

    @Test
    public void createWithoutBooking() {
        Comment comment = new Comment(1L, "Comment for item 1", TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, TestConstants.CURRENT_TIME);
        Mockito.when(bookingRepository.existsByBookerIdAndItemIdAndEndBefore(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(LocalDateTime.class))).thenReturn(false);

        Assertions.assertThrows(BadRequestException.class, () -> commentService.create(comment));
    }
}
