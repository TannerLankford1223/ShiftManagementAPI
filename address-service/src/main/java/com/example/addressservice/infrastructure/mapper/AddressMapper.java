package com.example.addressservice.infrastructure.mapper;

import com.example.addressservice.domain.dto.AddressDTO;
import com.example.addressservice.infrastructure.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDTO addressToAddressDTO(Address address);

    Address addressDTOToAddress(AddressDTO addressDTO);
}
