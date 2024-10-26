package com.example.gateway.controller;

import com.example.api.ItemApi;
import com.example.api.dto.ItemDto;
import com.example.gateway.client.ItemClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping(path = ItemApi.PATH)
@RequiredArgsConstructor
public class ItemController implements ItemApi {

    private final ItemClient itemClient;

    @Override
    public ResponseEntity<Object> create(ItemDto itemDto, long ownerId) {
        return itemClient.create(itemDto, ownerId);
    }

    @Override
    public ResponseEntity<Object> update(long itemId, ItemDto itemDto, long ownerId) {
        return itemClient.update(itemId, itemDto, ownerId);
    }

    @Override
    public ResponseEntity<Object> findById(long itemId, long userId) {
        return itemClient.findById(itemId, userId);
    }

    @Override
    public ResponseEntity<Object> findAllByOwnerId(long ownerId, int from, int size) {
        return itemClient.findAllByOwnerId(ownerId, from, size);
    }

    @Override
    public ResponseEntity<Object> search(String text, int from, int size) {
        if (text.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return itemClient.search(text, from, size);
    }
}
