package com.example.server.mapper;

import com.example.api.dto.ItemRequestDto;
import com.example.server.mapper.decorator.ItemRequestMapperDecorator;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;
import org.mapstruct.Context;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@DecoratedWith(ItemRequestMapperDecorator.class)
@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface ItemRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    ItemRequest toItemRequest(ItemRequestDto itemRequestDto, User requester);

    ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<Item> items);

    List<ItemRequestDto> toItemRequestDto(List<ItemRequest> itemRequests, @Context List<Item> items);
}
