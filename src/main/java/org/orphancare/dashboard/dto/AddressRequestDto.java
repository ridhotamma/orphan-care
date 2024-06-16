package org.orphancare.dashboard.dto;

import lombok.Data;

@Data
public class AddressRequestDto {
    private String street;
    private String urbanVillage;
    private String subdistrict;
    private String city;
    private String province;
    private String postalCode;
}
