package org.orphancare.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {

    private UUID id;
    private String street;
    private String urbanVillage;
    private String subdistrict;
    private String city;
    private String province;
    private String postalCode;
}
