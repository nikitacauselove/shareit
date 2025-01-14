package com.example.server.mapper;

import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemRequestDto;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRequestMapper {

    private final ItemMapper itemMapper;

    public ItemRequest toItemRequest(ItemRequestDto itemRequestDto, User requester) {
        if ( itemRequestDto == null && requester == null ) {
            return null;
        }

        ItemRequest itemRequest = new ItemRequest();

        if ( itemRequestDto != null ) {
            itemRequest.setDescription( itemRequestDto.getDescription() );
        }
        itemRequest.setRequester( requester );

        return itemRequest;
    }

    public ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<Item> items) {
        if ( itemRequest == null && items == null ) {
            return null;
        }

        Long requesterId = null;
        Long id = null;
        String description = null;
        LocalDateTime created = null;
        if ( itemRequest != null ) {
            requesterId = itemRequestRequesterId( itemRequest );
            id = itemRequest.getId();
            description = itemRequest.getDescription();
            created = itemRequest.getCreated();
        }
        List<ItemDto> items1 = null;
        items1 = itemMapper.toItemDto( items );

        ItemRequestDto itemRequestDto = new ItemRequestDto( id, description, requesterId, created, items1 );

        return itemRequestDto;
    }

    public List<ItemRequestDto> toItemRequestDto(List<ItemRequest> itemRequests, List<Item> items) {
        Map<Long, List<Item>> map = items.stream()
                .collect(Collectors.groupingBy(item -> item.getRequest().getId()));

        return itemRequests.stream()
                .map(itemRequest -> toItemRequestDto(itemRequest, map.getOrDefault(itemRequest.getId(), Collections.emptyList())))
                .collect(Collectors.toList());
    }

    private Long itemRequestRequesterId(ItemRequest itemRequest) {
        User requester = itemRequest.getRequester();
        if ( requester == null ) {
            return null;
        }
        return requester.getId();
    }

    protected ItemRequestDto itemRequestToItemRequestDto(ItemRequest itemRequest, List<Item> items) {
        if ( itemRequest == null ) {
            return null;
        }

        Long id = null;
        String description = null;
        LocalDateTime created = null;

        id = itemRequest.getId();
        description = itemRequest.getDescription();
        created = itemRequest.getCreated();

        Long requesterId = null;
        List<ItemDto> items1 = null;

        ItemRequestDto itemRequestDto = new ItemRequestDto( id, description, requesterId, created, items1 );

        return itemRequestDto;
    }
}
