package com.example.addressservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {

    @JsonProperty("store_id")
    private String storeId;

    @JsonProperty("store_address")
    @NotNull
    private String address;

    @JsonProperty("city")
    @NotNull
    private String city;

    @JsonProperty("state")
    @NotNull
    private String state;

    @JsonProperty("zip_code")
    @NotNull
    @Size(min = 5, max = 5, message = "Zip Code must be 5 digits in length")
    private int zipCode;

    public AddressDTO(String address, String city, String state, int zipCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}
