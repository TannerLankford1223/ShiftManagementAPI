package com.example.addressservice.domain.service;

import com.example.addressservice.domain.dto.AddressDTO;
import com.example.addressservice.domain.ports.api.AddressServicePort;
import com.example.addressservice.domain.ports.spi.AddressPersistencePort;
import com.example.addressservice.infrastructure.mapper.AddressMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements AddressServicePort {

    private final AddressPersistencePort addressRepo;

    private final AddressMapper mapper;

    public AddressService(AddressPersistencePort addressRepo, AddressMapper mapper) {
        this.addressRepo = addressRepo;
        this.mapper = mapper;
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
