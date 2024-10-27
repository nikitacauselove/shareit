package com.example.gateway.client;

import com.example.api.BookingApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "bookingClient", url = "http://localhost:9090", path = BookingApi.PATH)
public interface BookingClient extends BookingApi {
}
