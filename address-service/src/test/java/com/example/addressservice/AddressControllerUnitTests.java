package com.example.addressservice;

import com.example.addressservice.application.controller.AddressController;
import com.example.addressservice.domain.dto.AddressDTO;
import com.example.addressservice.domain.ports.api.AddressServicePort;
import com.example.addressservice.infrastructure.entity.Address;
import com.example.addressservice.infrastructure.mapper.AddressMapper;
import com.example.addressservice.infrastructure.mapper.AddressMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
public class AddressControllerUnitTests {

    @MockBean
    private AddressServicePort addressService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private AddressMapper addressMapper;

    private AddressDTO request;
    private Address address;
    private AddressDTO response;


    @BeforeEach
    public void init() {
        this.addressMapper = new AddressMapperImpl();
        this.request = new AddressDTO(1001L, "123 Main Street", "City",
                "State", "12345");
        this.address = addressMapper.addressDTOToAddress(request);
        this.response = addressMapper.addressToAddressDTO(address);
    }

    @Test
    public void saveAddress_ReturnsAddressDTO() throws Exception {
        Mockito.when(addressService.saveAddress(request)).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/address/new-address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.store_id").value(address.getStoreId()))
                .andExpect(jsonPath("$.store_address").value(address.getAddress()))
                .andExpect(jsonPath("$.city").value(address.getCity()))
                .andExpect(jsonPath("$.state").value(address.getState()))
                .andExpect(jsonPath("$.zip_code").value(address.getZipCode()));
    }

    @Test
    public void getAddress_ReturnsAddressDTO() throws Exception {
        Mockito.when(addressService.getAddress(request.getStoreId())).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/address/{storeId}", request.getStoreId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.store_id").value(address.getStoreId()))
                .andExpect(jsonPath("$.store_address").value(address.getAddress()))
                .andExpect(jsonPath("$.city").value(address.getCity()))
                .andExpect(jsonPath("$.state").value(address.getState()))
                .andExpect(jsonPath("$.zip_code").value(address.getZipCode()));
    }

    @Test
    public void getAddressList_ReturnsListOfAddressDTOs() throws Exception {
        AddressDTO response1 = new AddressDTO(542L, "111 East Briar Lane", "Nashville",
                "Tennessee", "89532");
        AddressDTO response2 = new AddressDTO(16L, "3089 West Grand Boulevard", "Kansas City",
                "Missouri", "11111");

        Mockito.when(addressService.getAddresses()).thenReturn(List.of(response, response1, response2));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/address/address-list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    public void updateAddress_ReturnsAddressDTO() throws Exception {
        AddressDTO update = new AddressDTO(1001L, "1421 West Main Street", "Philadelphia",
                "Pennsylvania", "54321");

        Mockito.when(addressService.updateAddress(update)).thenReturn(update);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/address/update/{storeId}", update.getStoreId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.store_id").value(update.getStoreId()))
                .andExpect(jsonPath("$.store_address").value(update.getAddress()))
                .andExpect(jsonPath("$.city").value(update.getCity()))
                .andExpect(jsonPath("$.state").value(update.getState()))
                .andExpect(jsonPath("$.zip_code").value(update.getZipCode()));
    }

    @Test
    public void deleteAddress_ReturnsStatus200() throws Exception {
        Mockito.doNothing().when(addressService).deleteAddress(address.getStoreId());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/address/{storeId}", address.getStoreId()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("Store with id " + address.getStoreId() + " deleted", content);
    }
}
