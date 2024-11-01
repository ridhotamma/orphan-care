package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDto {

    @Size(max = 255)
    private String street;

    @Size(max = 255)
    private String village;

    @Size(max = 255)
    private String district;

    @Size(max = 255)
    private String regency;

    @Size(max = 255)
    private String province;

    @Pattern(regexp = "^[0-9]{5}$|^$", message = "Postal code must be exactly 5 digits or empty")
    private String postalCode;

    private ProvinceDto provinceDetail;
    private RegencyDto regencyDetail;
    private DistrictDto districtDetail;
    private VillageDto villageDetail;

    @Data
    public static class ProvinceDto {
        private String id;
        private String name;
    }

    @Data
    public static class RegencyDto {
        private String id;
        private String provinceId;
        private String name;
    }

    @Data
    public static class DistrictDto {
        private String id;
        private String regencyId;
        private String name;
    }

    @Data
    public static class VillageDto {
        private String id;
        private String districtId;
        private String name;
    }
}
