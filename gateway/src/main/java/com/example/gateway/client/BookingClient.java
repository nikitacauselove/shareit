package com.example.gateway.client;

import com.example.api.BookingApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "bookingClient", url = "${application.url}", path = BookingApi.PATH)
public interface BookingClient extends BookingApi {
}
