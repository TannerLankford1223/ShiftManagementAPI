package com.example.addressservice.infrastructure.adapters;

import com.example.addressservice.domain.ports.spi.AddressPersistencePort;
import com.example.addressservice.infrastructure.entity.Address;
import com.example.addressservice.infrastructure.persistence.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressJpaAdapter implements AddressPersistencePort {

    private final AddressRepository addressRepo;

    public AddressJpaAdapter(AddressRepository addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public Optional<Address> getAddress(long storeId) {
        return addressRepo.findById(storeId);
    }

    @Override
    public List<Address> getAddresses() {
        return addressRepo.findAll();
    }

    @Override
    public void deleteAddress(long storeId) {
        addressRepo.deleteByStoreId(storeId);
    }

    @Override
    public boolean addressExists(long storeId) {
        return addressRepo.existsById(storeId);
    }
}
