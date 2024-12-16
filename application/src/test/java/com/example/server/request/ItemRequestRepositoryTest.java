package com.example.server.request;

import com.example.server.TestConstants;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.entity.Item;
import com.example.server.repository.ItemRequestRepository;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemRequestRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemRequestRepository itemRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository.save(TestConstants.FIRST_USER);
        userRepository.save(TestConstants.SECOND_USER);
        userRepository.save(TestConstants.THIRD_USER);
        userRepository.save(TestConstants.FOURTH_USER);

        itemRepository.save(TestConstants.FIRST_ITEM);
        itemRepository.save(TestConstants.SECOND_ITEM);
        itemRepository.save(TestConstants.THIRD_ITEM);
        itemRepository.save(TestConstants.FOURTH_ITEM);
    }

    @Test
    public void findAllByRequesterId() {
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        Item item = new Item(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, TestConstants.SECOND_USER, itemRequest);
        itemRequestRepository.save(itemRequest);
        itemRepository.save(item);

        Assertions.assertEquals(List.of(itemRequest), itemRequestRepository.findAllByRequesterId(1L, Sort.by(Sort.Direction.DESC, "created")));
    }

    @Test
    public void findAllByRequesterIdNot() {
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        Item item = new Item(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, TestConstants.SECOND_USER, itemRequest);
        itemRequestRepository.save(itemRequest);
        itemRepository.save(item);

        Assertions.assertEquals(List.of(itemRequest), itemRequestRepository.findAllByRequesterIdNot(2L, PageRequest.of(0, 10)));
    }
}
