package com.example.server.mapper;

import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.mapper.decorator.ItemMapperDecorator;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;
import org.mapstruct.Context;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(ItemMapperDecorator.class)
@Mapper(componentModel = "spring", uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "itemDto.name")
    @Mapping(target = "description", source = "itemDto.description")
    @Mapping(target = "request", source = "itemRequest")
    Item toItem(ItemDto itemDto, User owner, ItemRequest itemRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "itemDto.name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "available", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Item updateItem(ItemDto itemDto, @MappingTarget Item item);

    @Mapping(target = "requestId", source = "item.request.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ItemDto toItemDto(Item item);

    List<ItemDto> toItemDto(List<Item> items);

    ItemDtoWithBookings toItemDtoWithBookings(Item item, List<Booking> bookings, List<Comment> comments);

    @Mapping(target = "lastBooking", ignore = true)
    @Mapping(target = "nextBooking", ignore = true)
    ItemDtoWithBookings toItemDtoWithBookings(Item item, List<Comment> comments);

    List<ItemDtoWithBookings> toItemDtoWithBookings(List<Item> items, @Context List<Booking> bookings, @Context List<Comment> comments);
}
