package com.example.addressservice.infrastructure.persistence;

import com.example.addressservice.infrastructure.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    void deleteByStoreId(long storeId);
}
