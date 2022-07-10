package com.example.addressservice.application.controller;

import com.example.addressservice.domain.dto.AddressDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/address")
public class AddressController {

    @PostMapping("/new-address")
    public AddressDTO saveAddress(AddressDTO addressDTO) {
        return null;
    }

    @GetMapping("/{storeId}")
    public AddressDTO getAddress(@PathVariable String storeId) {
        return null;
    }

    @GetMapping("")
    public List<AddressDTO> getAddressList() {
        return null;
    }

    @GetMapping("/{state}")
    public List<AddressDTO> getAddressListByState(@PathVariable String state) {
        return null;
    }

    @PutMapping("/update/{storeId}")
    public AddressDTO updateAddress(@PathVariable String storeId, AddressDTO addressDTO) {
        return null;
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<String> deleteAddress(@PathVariable String storeId) {
        return null;
    }
}
