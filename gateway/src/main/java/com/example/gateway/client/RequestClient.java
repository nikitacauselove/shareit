package com.example.gateway.client;

import com.example.api.dto.ItemRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Service
public class RequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public RequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> create(ItemRequestDto itemRequestDto, long requesterId) {
        return post("", requesterId, itemRequestDto);
    }

    public ResponseEntity<Object> findById(long requestId, long requesterId) {
        return get(String.format("/%d", requestId), requesterId);
    }

    public ResponseEntity<Object> findAllByRequesterId(long requesterId) {
        return get("", requesterId);
    }

    public ResponseEntity<Object> findAllByRequesterIdNot(long requesterId, int from, int size) {
        return get(String.format("/all?from=%s&size=%s", from, size), requesterId);
    }
}
