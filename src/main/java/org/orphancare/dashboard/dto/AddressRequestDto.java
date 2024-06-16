package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {

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
