package com.example.gateway.client;

import com.example.api.CommentApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "commentClient", url = "http://localhost:9090", path = CommentApi.PATH)
public interface CommentClient extends CommentApi {
}
