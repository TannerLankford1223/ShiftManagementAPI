package com.example.addressservice.domain.ports.api;

import com.example.addressservice.domain.dto.AddressDTO;

import java.util.List;

public interface AddressServicePort {

    AddressDTO saveAddress(AddressDTO addressDTO);

    AddressDTO getAddress(String storeId);

    List<AddressDTO> getAddresses();

    List<AddressDTO> getAddressesInState(String state);

    AddressDTO updateAddress(AddressDTO addressDTO);

    void deleteAddress(String storeId);

    boolean addressExists(String storeId);
}
