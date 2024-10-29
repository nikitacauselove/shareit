package com.example.gateway.client;

import com.example.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "userClient", url = "${application.url}", path = UserClient.PATH)
public interface UserClient extends UserApi {
}
