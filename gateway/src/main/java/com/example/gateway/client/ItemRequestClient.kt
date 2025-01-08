package com.example.gateway.client

import com.example.api.ItemRequestApi
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "requestClient", url = "\${application.url}", path = ItemRequestApi.PATH)
interface ItemRequestClient : ItemRequestApi
