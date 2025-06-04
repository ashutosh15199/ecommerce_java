package com.example.ecommerce.modals;

import lombok.Data;

@Data
public class AddressDTO {
    private String recipientName;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phoneNumber;
}
