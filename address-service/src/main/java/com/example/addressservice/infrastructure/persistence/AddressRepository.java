package com.example.addressservice.infrastructure.persistence;

import com.example.addressservice.infrastructure.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    List<Address> findAllByState(String state);

    Address deleteByStoreId(String storeId);
}
