package com.example.addressservice.domain.ports.api;

import com.example.addressservice.domain.dto.AddressDTO;

import java.util.List;

public interface AddressServicePort {

    AddressDTO saveAddress(AddressDTO addressDTO);

    AddressDTO getAddress(long storeId);

    List<AddressDTO> getAddresses();

    AddressDTO updateAddress(AddressDTO addressDTO);

    void deleteAddress(long storeId);

    boolean addressExists(long storeId);
}
