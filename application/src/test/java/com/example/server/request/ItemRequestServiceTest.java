package com.example.server.request;

import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemRequestDto;
import com.example.server.TestConstants;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.entity.Item;
import com.example.server.repository.ItemRequestRepository;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.service.impl.ItemRequestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceTest {
    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Test
    public void create() {
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "Хотел бы воспользоваться щёткой для обуви", 1L, TestConstants.CURRENT_TIME, Collections.emptyList());
        Mockito.when(itemRequestRepository.save(Mockito.any(ItemRequest.class))).thenReturn(itemRequest);

        Assertions.assertEquals(itemRequestDto, itemRequestService.create(itemRequest));
    }

    @Test
    public void findById() {
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        Mockito.when(itemRequestRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(itemRequest));

        Assertions.assertEquals(itemRequest, itemRequestService.findById(1L));
    }

    @Test
    public void findByIdUnknown() {
        Assertions.assertThrows(NotFoundException.class, () -> itemRequestService.findById(99L));
    }

    @Test
    public void findByIdWithItems() {
        ItemDto itemDto = new ItemDto(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, 1L);
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "Хотел бы воспользоваться щёткой для обуви", 1L, TestConstants.CURRENT_TIME, List.of(itemDto));
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        Item item = new Item(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, TestConstants.SECOND_USER, itemRequest);
        Mockito.when(itemRequestRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(itemRequest));
        Mockito.when(itemRepository.findAllByRequestId(Mockito.anyLong())).thenReturn(List.of(item));

        Assertions.assertEquals(itemRequestDto, itemRequestService.findByIdWithItems(1L));
    }

    @Test
    public void findAllByRequesterId() {
        ItemDto itemDto = new ItemDto(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, 1L);
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "Хотел бы воспользоваться щёткой для обуви", 1L, TestConstants.CURRENT_TIME, List.of(itemDto));
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        Item item = new Item(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, TestConstants.SECOND_USER, itemRequest);
        Mockito.when(itemRequestRepository.findAllByRequesterId(Mockito.anyLong(), Mockito.any(Sort.class))).thenReturn(List.of(itemRequest));
        Mockito.when(itemRepository.findAllByRequestIdNotNull()).thenReturn(List.of(item));

        Assertions.assertEquals(List.of(itemRequestDto), itemRequestService.findAllByRequesterId(1L));
    }

    @Test
    public void findAllByRequesterIdNot() {
        ItemDto itemDto = new ItemDto(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, 1L);
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "Хотел бы воспользоваться щёткой для обуви", 1L, TestConstants.CURRENT_TIME, List.of(itemDto));
        ItemRequest itemRequest = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", TestConstants.FIRST_USER, TestConstants.CURRENT_TIME);
        Item item = new Item(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, TestConstants.SECOND_USER, itemRequest);
        Mockito.when(itemRequestRepository.findAllByRequesterIdNot(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(List.of(itemRequest));
        Mockito.when(itemRepository.findAllByRequestIdNotNull()).thenReturn(List.of(item));

        Assertions.assertEquals(List.of(itemRequestDto), itemRequestService.findAllByRequesterIdNot(2L, 0, 10));
    }
}
