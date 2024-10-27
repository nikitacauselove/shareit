package com.example.gateway.client;

import com.example.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "userClient", url = "http://localhost:9090", path = UserClient.PATH)
public interface UserClient extends UserApi {
}
