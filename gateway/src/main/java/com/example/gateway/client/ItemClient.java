package com.example.gateway.client;

import com.example.api.ItemApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "itemClient", url = "${application.url}", path = ItemApi.PATH)
public interface ItemClient extends ItemApi {
}
