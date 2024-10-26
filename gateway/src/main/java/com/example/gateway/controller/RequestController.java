package com.example.gateway.controller;

import com.example.api.RequestApi;
import com.example.api.dto.ItemRequestDto;
import com.example.gateway.client.RequestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = RequestApi.PATH)
@RequiredArgsConstructor
public class RequestController implements RequestApi {

    private final RequestClient requestClient;

    @Override
    public ResponseEntity<Object> create(ItemRequestDto itemRequestDto, long requesterId) {
        return requestClient.create(itemRequestDto, requesterId);
    }

    @Override
    public ResponseEntity<Object> findById(long requestId, long requesterId) {
        return requestClient.findById(requestId, requesterId);
    }

    @Override
    public ResponseEntity<Object> findAllByRequesterId(long requesterId) {
        return requestClient.findAllByRequesterId(requesterId);
    }

    @Override
    public ResponseEntity<Object> findAllByRequesterIdNot(long requesterId, int from, int size) {
        return requestClient.findAllByRequesterIdNot(requesterId, from, size);
    }
}
