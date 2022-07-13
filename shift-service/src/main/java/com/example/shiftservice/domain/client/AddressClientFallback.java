package com.example.shiftservice.domain.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AddressClientFallback implements AddressClient {

    @Override
    public boolean addressExists(long storeId) {
        log.error("Unable to connect to address-service");
        return false;
    }
}
