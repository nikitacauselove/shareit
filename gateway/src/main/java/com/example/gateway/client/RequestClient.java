package com.example.gateway.client;

import com.example.api.RequestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "requestClient", url = "${application.url}", path = RequestApi.PATH)
public interface RequestClient extends RequestApi {
}
