package com.example.gateway.client;

import com.example.api.RequestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "requestClient", url = "http://localhost:9090", path = RequestApi.PATH)
public interface RequestClient extends RequestApi {
}
