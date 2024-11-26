package com.example.server.mapper;

import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemRequestDto;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRequestMapper {

    private final ItemMapper itemMapper;

    public ItemRequest toItemRequest(ItemRequestDto itemRequestDto, User requester) {
        return new ItemRequest(null, itemRequestDto.description(), requester, LocalDateTime.now());
    }

    public ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<Item> items) {
        List<ItemDto> listOfItemDto = itemMapper.toItemDto(items);

        return new ItemRequestDto(itemRequest.getId(), itemRequest.getDescription(), itemRequest.getRequester().getId(), itemRequest.getCreated(), listOfItemDto);
    }

    public List<ItemRequestDto> toItemRequestDto(List<ItemRequest> itemRequests, List<Item> items) {
        return itemRequests.stream()
                .map(itemRequest -> {
                    List<Item> listOfItems = items.stream().filter(item -> itemRequest.equals(item.getRequest())).collect(Collectors.toList());

                    return this.toItemRequestDto(itemRequest, listOfItems);
                })
                .collect(Collectors.toList());
    }
}
