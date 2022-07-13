package com.example.shiftservice.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-service", fallback = AddressClientFallback.class)
public interface AddressClient {

    @GetMapping("/api/address/{storeId}/check")
    boolean addressExists(@PathVariable long storeId);
}