package com.example.server.comment;

import com.example.server.TestConstants;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.CommentRepository;
import com.example.server.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository.save(TestConstants.FIRST_USER);
        userRepository.save(TestConstants.SECOND_USER);

        itemRepository.save(TestConstants.FIRST_ITEM);
        itemRepository.save(TestConstants.SECOND_ITEM);
    }

    @Test
    public void findAllByItemId() {
        Comment comment = new Comment(1L, "Add comment from user1", TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        commentRepository.save(comment);

        Assertions.assertEquals(List.of(comment), commentRepository.findAllByItemId(2));
    }
}
