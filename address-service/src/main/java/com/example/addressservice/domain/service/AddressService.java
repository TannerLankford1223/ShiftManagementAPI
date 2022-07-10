package com.example.addressservice.domain.service;

import com.example.addressservice.domain.dto.AddressDTO;
import com.example.addressservice.domain.ports.api.AddressServicePort;
import com.example.addressservice.domain.ports.spi.AddressPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements AddressServicePort {

    private final AddressPersistencePort addressRepo;

    public AddressService(AddressPersistencePort addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public AddressDTO saveAddress(AddressDTO addressDTO) {
        return null;
    }

    @Override
    public AddressDTO getAddress(String storeId) {
        return null;
    }

    @Override
    public List<AddressDTO> getAddresses() {
        return null;
    }

    @Override
    public List<AddressDTO> getAddressesInState(String state) {
        return null;
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        return null;
    }

    @Override
    public void deleteAddress(String storeId) {

    }
}
