package com.example.addressservice.domain.service;

import com.example.addressservice.domain.dto.AddressDTO;
import com.example.addressservice.domain.ports.api.AddressServicePort;
import com.example.addressservice.domain.ports.spi.AddressPersistencePort;
import com.example.addressservice.infrastructure.entity.Address;
import com.example.addressservice.infrastructure.exceptionhandler.InvalidRequestException;
import com.example.addressservice.infrastructure.mapper.AddressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressService implements AddressServicePort {

    private final AddressPersistencePort addressRepo;

    private final AddressMapper mapper;

    public AddressService(AddressPersistencePort addressRepo, AddressMapper mapper) {
        this.addressRepo = addressRepo;
        this.mapper = mapper;
    }

    @Override
    public AddressDTO saveAddress(AddressDTO addressDTO) {
        Address address = mapper.addressDTOToAddress(addressDTO);
        log.info("Store with id " + address.getStoreId() + " added");
        return mapper.addressToAddressDTO(addressRepo.saveAddress(address));
    }

    @Override
    public AddressDTO getAddress(String storeId) {
        Optional<Address> addressOpt = addressRepo.getAddress(storeId);

        if (addressOpt.isPresent()) {
            return mapper.addressToAddressDTO(addressOpt.get());
        }

        log.error("Store with id " + storeId + " not found");
        throw new InvalidRequestException("Store with id " + storeId + " not found");
    }

    @Override
    public List<AddressDTO> getAddresses() {
        return convertToAddressDTOList(addressRepo.getAddresses());
    }

    @Override
    public List<AddressDTO> getAddressesInState(String state) {
        return convertToAddressDTOList(addressRepo.getAddressesInState(state));
    }

    private List<AddressDTO> convertToAddressDTOList(List<Address> addresses) {
        return addresses.stream()
                .map(mapper::addressToAddressDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        Optional<Address> addressOpt = addressRepo.getAddress(addressDTO.getStoreId());

        if (addressOpt.isPresent()) {
            Address address = addressOpt.get();
            address.setAddress(addressDTO.getAddress());
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setZipCode(addressDTO.getZipCode());

           return mapper.addressToAddressDTO(addressRepo.saveAddress(address));
        }

        log.error("Store with id " + addressDTO.getStoreId() + " not found");
        throw new InvalidRequestException("Store with id " + addressDTO.getStoreId() + " not found");
    }

    @Override
    public void deleteAddress(String storeId) {
        Address deletedAddress = addressRepo.deleteAddress(storeId);

        if (deletedAddress == null) {
            log.error("Store with id " + storeId + " not found");
            throw new InvalidRequestException("Store with id " + storeId + " not found");
        }
    }
}
