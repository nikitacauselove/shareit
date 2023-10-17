package com.example.gateway.booking;

import com.example.gateway.booking.dto.BookingCreationDto;
import com.example.gateway.booking.model.BookingState;
import com.example.gateway.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> create(BookingCreationDto bookingCreationDto, long bookerId) {
        return post("", bookerId, bookingCreationDto);
    }

    public ResponseEntity<Object> approveOrReject(long bookingId, long ownerId, boolean approved) {
        return patch(String.format("/%d?approved=%s", bookingId, approved), ownerId);
    }

    public ResponseEntity<Object> findById(long bookingId, long userId) {
        return get(String.format("/%d", bookingId), userId);
    }

    public ResponseEntity<Object> findAllByBookerId(long bookerId, BookingState state, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );

        return get("?state={state}&from={from}&size={size}", bookerId, parameters);
    }

    public ResponseEntity<Object> findAllByOwnerId(long bookerId, BookingState state, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "state", state.name(),
                "from", from,
                "size", size
        );

        return get("/owner?state={state}&from={from}&size={size}", bookerId, parameters);
    }
}
