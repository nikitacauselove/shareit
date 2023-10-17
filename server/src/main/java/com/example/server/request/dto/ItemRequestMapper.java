package com.example.server.request.dto;

import com.example.server.item.dto.ItemDto;
import com.example.server.item.dto.ItemMapper;
import com.example.server.item.model.Item;
import com.example.server.request.model.ItemRequest;
import com.example.server.user.model.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ItemRequestMapper {
    public ItemRequest toItemRequest(ItemRequestDto itemRequestDto, User requester) {
        return new ItemRequest(null, itemRequestDto.getDescription(), requester, LocalDateTime.now());
    }

    public ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<Item> items) {
        List<ItemDto> listOfItemDto = ItemMapper.toItemDto(items);

        return new ItemRequestDto(itemRequest.getId(), itemRequest.getDescription(), itemRequest.getRequester().getId(), itemRequest.getCreated(), listOfItemDto);
    }

    public List<ItemRequestDto> toItemRequestDto(List<ItemRequest> itemRequests, List<Item> items) {
        return itemRequests.stream()
                .map(itemRequest -> {
                    List<Item> listOfItems = items.stream().filter(item -> itemRequest.equals(item.getRequest())).collect(Collectors.toList());

                    return ItemRequestMapper.toItemRequestDto(itemRequest, listOfItems);
                })
                .collect(Collectors.toList());
    }
}
