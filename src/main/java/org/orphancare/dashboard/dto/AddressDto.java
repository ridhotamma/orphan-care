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
