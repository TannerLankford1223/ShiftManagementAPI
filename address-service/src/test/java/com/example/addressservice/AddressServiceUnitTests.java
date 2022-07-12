package com.example.addressservice;

import com.example.addressservice.domain.dto.AddressDTO;
import com.example.addressservice.domain.ports.api.AddressServicePort;
import com.example.addressservice.domain.ports.spi.AddressPersistencePort;
import com.example.addressservice.domain.service.AddressService;
import com.example.addressservice.infrastructure.entity.Address;
import com.example.addressservice.infrastructure.exceptionhandler.InvalidRequestException;
import com.example.addressservice.infrastructure.mapper.AddressMapper;
import com.example.addressservice.infrastructure.mapper.AddressMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AddressServiceUnitTests {

    @Mock
    AddressPersistencePort addressRepo;

    private AddressServicePort addressService;

    private AddressMapper mapper;

    private AddressDTO request;
    private Address address;

    @BeforeEach
    public void init() {
        this.mapper = new AddressMapperImpl();
        this.addressService = new AddressService(addressRepo, mapper);
        this.request = new AddressDTO(452L,"123 Main Street", "City",
                "State", 12345);
        this.address = mapper.addressDTOToAddress(request);
    }


    @Test
    public void saveAddress_ReturnsAddressDTO() {
        Mockito.when(addressRepo.saveAddress(address)).thenReturn(address);

        AddressDTO response = addressService.saveAddress(request);

        verify(addressRepo).saveAddress(address);
        assertEquals(452L, response.getStoreId());
        assertEquals("123 Main Street", response.getAddress());
        assertEquals("City", response.getCity());
        assertEquals("State", response.getState());
        assertEquals(12345, response.getZipCode());
    }

    @Test
    public void getAddress_AddressExists_ReturnsAddressDTO() {
        Mockito.when(addressRepo.getAddress(address.getStoreId())).thenReturn(Optional.of(address));

        AddressDTO response = addressService.getAddress(address.getStoreId());

        assertEquals(452L, response.getStoreId());
        assertEquals("123 Main Street", response.getAddress());
        assertEquals("City", response.getCity());
        assertEquals("State", response.getState());
        assertEquals(12345, response.getZipCode());
    }

    @Test
    public void getAddress_AddressNonExistent_ThrowsInvalidRequestException() {
        Mockito.when(addressRepo.getAddress(5000L)).thenReturn(Optional.empty());

        assertThrows(InvalidRequestException.class, () -> addressService.getAddress(5000L));
    }

    @Test
    public void getAddresses_ReturnsListOfAddressDTOs() {
        Address address1 = new Address(483L,"1321 Maryland Street", "City",
                "State", 11111);
        Address address2 = new Address(8321L,"9220 Pennsylvania Avenue", "City",
                "State", 22222);

        Mockito.when(addressRepo.getAddresses()).thenReturn(List.of(address, address1, address2));

        List<AddressDTO> addresses = addressService.getAddresses();

        assertEquals(3, addresses.size());
    }

    @Test
    public void getAddressesInState_ReturnsListOfAddressDTOsFromState() {
        Address address1 = new Address(483L,"1321 Maryland Street", "City",
                "State", 11111);
        Address address2 = new Address(8321L,"9220 Pennsylvania Avenue", "City",
                "State", 22222);
        Address address3 = new Address(8321L,"909 Broadway Boulevard", "City",
                "State", 33333);

        Mockito.when(addressRepo.getAddressesInState(address.getState()))
                .thenReturn(List.of(address, address1, address2, address3));

        List<AddressDTO> addresses = addressService.getAddressesInState(address.getState());

        assertEquals(4, addresses.size());
    }

    @Test
    public void updateAddress_AddressExists_ReturnsAddressDTO() {
        AddressDTO update = new AddressDTO(452L, "1421 West Main Street", "Philadelphia",
                "Pennsylvania", 54321);

        Address updatedAddress = mapper.addressDTOToAddress(update);

        Mockito.when(addressRepo.getAddress(update.getStoreId())).thenReturn(Optional.of(address));
        Mockito.when(addressRepo.saveAddress(updatedAddress)).thenReturn(updatedAddress);

        AddressDTO response = addressService.updateAddress(update);

        assertEquals(452L, response.getStoreId());
        assertEquals("1421 West Main Street", response.getAddress());
        assertEquals("Philadelphia", response.getCity());
        assertEquals("Pennsylvania", response.getState());
        assertEquals(54321, response.getZipCode());
    }

    @Test
    public void updateAddress_AddressNonExistent_ThrowsInvalidRequestException() {
        Mockito.when(addressRepo.getAddress(address.getStoreId())).thenReturn(Optional.empty());
        assertThrows(InvalidRequestException.class, () -> addressService.updateAddress(request));
    }

    @Test
    public void deleteAddress_AddressExists_DeletedSuccessfully() {
        Mockito.when(addressRepo.addressExists(address.getStoreId())).thenReturn(true);
        Mockito.doNothing().when(addressRepo).deleteAddress(address.getStoreId());

        addressService.deleteAddress(address.getStoreId());

        verify(addressRepo).deleteAddress(address.getStoreId());
    }

    @Test
    public void deleteAddress_AddressNonExistent_ThrowsInvalidRequestException() {
        Mockito.when(addressRepo.addressExists(5000L)).thenReturn(false);

        assertThrows(InvalidRequestException.class, () -> addressService.deleteAddress(5000L));
    }

}
