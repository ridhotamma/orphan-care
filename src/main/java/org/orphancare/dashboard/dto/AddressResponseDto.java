package org.orphancare.dashboard.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AddressResponseDto {
    private UUID id;
    private String street;
    private String urbanVillage;
    private String subdistrict;
    private String city;
    private String province;
    private String postalCode;
}
