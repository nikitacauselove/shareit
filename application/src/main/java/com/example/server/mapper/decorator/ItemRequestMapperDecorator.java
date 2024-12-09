package com.example.server.mapper.decorator;

import com.example.api.dto.ItemRequestDto;
import com.example.server.mapper.ItemRequestMapper;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter(onMethod_ = @Autowired)
public abstract class ItemRequestMapperDecorator implements ItemRequestMapper {

    private ItemRequestMapper delegate;

    @Override
    public List<ItemRequestDto> toItemRequestDto(List<ItemRequest> itemRequests, List<Item> items) {
        Map<Long, List<Item>> map = items.stream()
                .collect(Collectors.groupingBy(item -> item.getRequest().getId()));

        return itemRequests.stream()
                .map(itemRequest -> toItemRequestDto(itemRequest, map.getOrDefault(itemRequest.getId(), Collections.emptyList())))
                .collect(Collectors.toList());
    }
}
