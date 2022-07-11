package com.example.shiftservice.domain.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-service", fallback = AddressFallback.class)
public interface AddressClient {

    @GetMapping("/api/address/{storeId}/check")
    boolean addressExists(@PathVariable String storeId);
}

@Slf4j
class AddressFallback implements AddressClient {

    @Override
    public boolean addressExists(String storeId) {
        log.error("Unable to connect to address-service server");
        return false;
    }
}