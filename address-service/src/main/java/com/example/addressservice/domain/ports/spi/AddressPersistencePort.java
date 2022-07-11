package com.example.addressservice.domain.ports.spi;

import com.example.addressservice.infrastructure.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressPersistencePort {

    Address saveAddress(Address address);

    Optional<Address> getAddress(String storeId);

    List<Address> getAddresses();

    List<Address> getAddressesInState(String state);

    void deleteAddress(String storeId);

    boolean addressExists(String storeId);
}
