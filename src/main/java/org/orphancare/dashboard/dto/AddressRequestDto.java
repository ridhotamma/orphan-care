package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {

    @NotBlank
    @Size(max = 255)
    private String street;

    @NotBlank
    @Size(max = 255)
    private String urbanVillage;

    @NotBlank
    @Size(max = 255)
    private String subdistrict;

    @NotBlank
    @Size(max = 255)
    private String city;

    @NotBlank
    @Size(max = 255)
    private String province;

    @NotBlank
    @Size(max = 5)
    private String postalCode;
}
