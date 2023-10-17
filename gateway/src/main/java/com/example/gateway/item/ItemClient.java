package com.example.gateway.item;

import com.example.gateway.client.BaseClient;
import com.example.gateway.item.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> create(ItemDto itemDto, long ownerId) {
        return post("", ownerId, itemDto);
    }

    public ResponseEntity<Object> update(long itemId, ItemDto itemDto, long ownerId) {
        return patch(String.format("/%d", itemId), ownerId, itemDto);
    }

    public ResponseEntity<Object> findById(long itemId, long userId) {
        return get(String.format("/%d", itemId), userId);
    }

    public ResponseEntity<Object> findAllByOwnerId(long ownerId, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );

        return get("?from={from}&size={size}", ownerId, parameters);
    }

    public ResponseEntity<Object> search(String text, int from, int size) {
        return get(String.format("/search?text=%s&from=%s&size=%s", text, from, size));
    }
}
