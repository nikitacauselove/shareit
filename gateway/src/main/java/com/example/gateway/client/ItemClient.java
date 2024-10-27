package com.example.gateway.client;

import com.example.api.ItemApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "itemClient", url = "http://localhost:9090", path = ItemApi.PATH)
public interface ItemClient extends ItemApi {
}
