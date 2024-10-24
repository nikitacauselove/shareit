package com.example.gateway.client;

import com.example.api.CommentApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "commentFeignClient", url = "http://localhost:9090", path = "/items")
public interface CommentClient extends CommentApi {
}
