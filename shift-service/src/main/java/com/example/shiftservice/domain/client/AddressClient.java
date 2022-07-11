package com.example.shiftservice.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-service", url = "http://localhost:8484", fallback = AddressFallback.class)
public interface AddressClient {

    @GetMapping("/api/address/exists/{storeId}")
    boolean addressExists(@PathVariable String storeId);
}

class AddressFallback implements AddressClient {

    @Override
    public boolean addressExists(String storeId) {
        return false;
    }
}