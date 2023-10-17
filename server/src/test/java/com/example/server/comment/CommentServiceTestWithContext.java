package com.example.server.comment;

import com.example.server.TestConstants;
import com.example.server.booking.BookingRepository;
import com.example.server.booking.model.Booking;
import com.example.server.booking.model.BookingStatus;
import com.example.server.comment.model.Comment;
import com.example.server.item.ItemRepository;
import com.example.server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
public class CommentServiceTestWithContext {
    private final CommentService commentService;
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository.save(TestConstants.FIRST_USER);
        userRepository.save(TestConstants.SECOND_USER);

        itemRepository.save(TestConstants.FIRST_ITEM);
        itemRepository.save(TestConstants.SECOND_ITEM);
    }

    @Test
    public void create() {
        Booking booking = new Booking(1L, TestConstants.CURRENT_TIME, TestConstants.CURRENT_TIME, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Comment comment = new Comment(1L, "Add comment from user1", TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, TestConstants.CURRENT_TIME.plusSeconds(1));
        bookingRepository.save(booking);

        Assertions.assertEquals(comment, commentService.create(comment));
    }
}
