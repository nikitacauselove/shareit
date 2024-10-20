package com.example.server.item;

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
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemRepositoryTest {
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
    }

    @Test
    public void findAllByOwnerId() {
        Item item = new Item(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, TestConstants.FIRST_USER, null);
        itemRepository.save(item);

        Assertions.assertEquals(List.of(item), itemRepository.findAllByOwnerId(1, PageRequest.of(0, 10)));
    }

    @Test
    public void search() {
        Item item = new Item(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, TestConstants.FIRST_USER, null);
        Item secondItem = new Item(2L, "Отвертка", "Аккумуляторная отвертка", true, TestConstants.SECOND_USER, null);
        itemRepository.save(item);
        itemRepository.save(secondItem);

        Assertions.assertEquals(List.of(item, secondItem), itemRepository.search("аккУМУляторная".toLowerCase(), PageRequest.of(0, 10)));
    }

    @Test
    public void findAllByRequestId() {
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        Item item = new Item(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, TestConstants.FIRST_USER, null);
        Item secondItem = new Item(2L, "Отвертка", "Аккумуляторная отвертка", true, TestConstants.SECOND_USER, null);
        Item thirditem = new Item(3L, "Клей Момент", "Тюбик суперклея марки Момент", true, TestConstants.SECOND_USER, null);
        Item fourthItem = new Item(4L, "Кухонный стол", "Стол для празднования", true, TestConstants.FOURTH_USER, null);
        Item fifthItem = new Item(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, TestConstants.SECOND_USER, itemRequest);
        itemRequestRepository.save(itemRequest);
        itemRepository.save(item);
        itemRepository.save(secondItem);
        itemRepository.save(thirditem);
        itemRepository.save(fourthItem);
        itemRepository.save(fifthItem);

        Assertions.assertEquals(List.of(fifthItem), itemRepository.findAllByRequestId(1));
    }
}
