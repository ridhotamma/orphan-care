package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDto {

    @Size(max = 255)
    private String street;

    @Size(max = 255)
    private String urbanVillage;

    @Size(max = 255)
    private String subdistrict;

    @Size(max = 255)
    private String city;

    @Size(max = 255)
    private String province;

    @Size(max = 5)
    private String postalCode;
}
