package com.example.addressservice.application.controller;

import com.example.addressservice.domain.dto.AddressDTO;
import com.example.addressservice.domain.ports.api.AddressServicePort;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressServicePort addressService;

    public AddressController(AddressServicePort addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/new-address")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Saves Store information")
    public AddressDTO saveAddress(@Valid @RequestBody AddressDTO addressDTO) {
        return addressService.saveAddress(addressDTO);
    }

    @GetMapping("/{storeId}")
    @Operation(summary = "Returns store information, if it exists")
    public AddressDTO getAddress(@PathVariable long storeId) {
        return addressService.getAddress(storeId);
    }

    @GetMapping("/")
    @Operation(summary = "Returns a list of all store address information")
    public List<AddressDTO> getAddressList() {
        return addressService.getAddresses();
    }

    @PutMapping("/update/{storeId}")
    @Operation(summary = "Updates a store, if it exists")
    public AddressDTO updateAddress(@PathVariable long storeId, @Valid @RequestBody AddressDTO addressDTO) {
        addressDTO.setStoreId(storeId);
        return addressService.updateAddress(addressDTO);
    }

    @DeleteMapping("/{storeId}")
    @Operation(summary = "Deletes a store, if it exists")
    public ResponseEntity<String> deleteAddress(@PathVariable long storeId) {
        addressService.deleteAddress(storeId);
        return new ResponseEntity<>("Store with id " + storeId + " deleted", HttpStatus.OK);
    }

    @GetMapping("/{storeId}/check")
    @Operation(summary = "Returns a boolean indicating whether the store exists")
    public boolean addressExists(@PathVariable long storeId) {
        return addressService.addressExists(storeId);
    }
}
