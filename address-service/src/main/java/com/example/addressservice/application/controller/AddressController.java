package com.example.addressservice.application.controller;

import com.example.addressservice.domain.dto.AddressDTO;
import com.example.addressservice.domain.ports.api.AddressServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressServicePort addressService;

    public AddressController(AddressServicePort addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/new-address")
    public AddressDTO saveAddress(@RequestBody AddressDTO addressDTO) {
        return addressService.saveAddress(addressDTO);
    }

    @GetMapping("/{storeId}")
    public AddressDTO getAddress(@PathVariable String storeId) {
        return addressService.getAddress(storeId);
    }

    @GetMapping("/address-list")
    public List<AddressDTO> getAddressList() {
        return addressService.getAddresses();
    }

    @GetMapping("/address-list/{state}")
    public List<AddressDTO> getAddressListByState(@PathVariable String state) {
        return addressService.getAddressesInState(state);
    }

    @PutMapping("/update/{storeId}")
    public AddressDTO updateAddress(@PathVariable String storeId, @RequestBody AddressDTO addressDTO) {
        addressDTO.setStoreId(storeId);
        return addressService.updateAddress(addressDTO);
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<String> deleteAddress(@PathVariable String storeId) {
        addressService.deleteAddress(storeId);
        return new ResponseEntity<>("Store with id " + storeId + " deleted", HttpStatus.OK);
    }

    @GetMapping("/exists/{storeId}")
    public boolean addressExists(@PathVariable String storeId) {
        return addressService.addressExists(storeId);
    }
}
