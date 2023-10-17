package com.example.server.request;

import com.example.server.TestConstants;
import com.example.server.item.ItemRepository;
import com.example.server.item.dto.ItemDto;
import com.example.server.item.model.Item;
import com.example.server.request.dto.ItemRequestDto;
import com.example.server.request.model.ItemRequest;
import com.example.server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
public class ItemRequestServiceTestWithContext {
    private final ItemRequestService itemRequestService;
    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

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
    public void create() {
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "Хотел бы воспользоваться щёткой для обуви", 1L, TestConstants.CURRENT_TIME, Collections.emptyList());

        Assertions.assertEquals(itemRequestDto, itemRequestService.create(itemRequest));
    }

    @Test
    public void findById() {
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        itemRequestService.create(itemRequest);

        Assertions.assertEquals(itemRequest, itemRequestService.findById(1));
    }

    @Test
    public void findByIdWithItems() {
        ItemDto itemDto = new ItemDto(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, 1L);
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "Хотел бы воспользоваться щёткой для обуви", 1L, TestConstants.CURRENT_TIME, List.of(itemDto));
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        Item item = new Item(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, TestConstants.SECOND_USER, itemRequest);
        itemRequestRepository.save(itemRequest);
        itemRepository.save(item);

        Assertions.assertEquals(itemRequestDto, itemRequestService.findByIdWithItems(1));
    }

    @Test
    public void findAllByRequesterId() {
        ItemDto itemDto = new ItemDto(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, 1L);
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "Хотел бы воспользоваться щёткой для обуви", 1L, TestConstants.CURRENT_TIME, List.of(itemDto));
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        Item item = new Item(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, TestConstants.SECOND_USER, itemRequest);
        itemRequestRepository.save(itemRequest);
        itemRepository.save(item);

        Assertions.assertEquals(List.of(itemRequestDto), itemRequestService.findAllByRequesterId(1));
    }

    @Test
    public void findAllByRequesterIdNot() {
        ItemDto itemDto = new ItemDto(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, 1L);
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "Хотел бы воспользоваться щёткой для обуви", 1L, TestConstants.CURRENT_TIME, List.of(itemDto));
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        Item item = new Item(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, TestConstants.SECOND_USER, itemRequest);
        itemRequestRepository.save(itemRequest);
        itemRepository.save(item);

        Assertions.assertEquals(List.of(itemRequestDto), itemRequestService.findAllByRequesterIdNot(2, 0, 10));
    }
}
