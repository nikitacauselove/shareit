package com.example.server.mapper.decorator;

import com.example.api.dto.ItemRequestDto;
import com.example.server.mapper.ItemRequestMapper;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ItemRequestMapperDecorator implements ItemRequestMapper {

    private ItemRequestMapper delegate;

    @Autowired
    public void setDelegate(ItemRequestMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<ItemRequestDto> toItemRequestDto(List<ItemRequest> itemRequests, List<Item> items) {
        return itemRequests.stream()
                .map(itemRequest -> {
                    List<Item> listOfItems = items.stream()
                            .filter(item -> itemRequest.equals(item.getRequest()))
                            .collect(Collectors.toList());

                    return this.toItemRequestDto(itemRequest, listOfItems);
                })
                .collect(Collectors.toList());
    }
}
