package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.orphancare.dashboard.dto.AddressDto;
import org.orphancare.dashboard.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "provinceId", source = "provinceDetail.id")
    @Mapping(target = "regencyId", source = "regencyDetail.id")
    @Mapping(target = "districtId", source = "districtDetail.id")
    @Mapping(target = "villageId", source = "villageDetail.id")
    Address toEntity(AddressDto dto);

    @Mapping(target = "provinceDetail", expression = "java(mapProvinceDetail(entity))")
    @Mapping(target = "regencyDetail", expression = "java(mapRegencyDetail(entity))")
    @Mapping(target = "districtDetail", expression = "java(mapDistrictDetail(entity))")
    @Mapping(target = "villageDetail", expression = "java(mapVillageDetail(entity))")
    AddressDto toDto(Address entity);

    default AddressDto.ProvinceDto mapProvinceDetail(Address entity) {
        if (entity.getProvinceId() == null) return null;
        AddressDto.ProvinceDto dto = new AddressDto.ProvinceDto();
        dto.setId(entity.getProvinceId());
        dto.setName(entity.getProvince());
        return dto;
    }

    default AddressDto.RegencyDto mapRegencyDetail(Address entity) {
        if (entity.getRegencyId() == null) return null;
        AddressDto.RegencyDto dto = new AddressDto.RegencyDto();
        dto.setId(entity.getRegencyId());
        dto.setProvinceId(entity.getProvinceId());
        dto.setName(entity.getRegency());
        return dto;
    }

    default AddressDto.DistrictDto mapDistrictDetail(Address entity) {
        if (entity.getDistrictId() == null) return null;
        AddressDto.DistrictDto dto = new AddressDto.DistrictDto();
        dto.setId(entity.getDistrictId());
        dto.setRegencyId(entity.getRegencyId());
        dto.setName(entity.getDistrict());
        return dto;
    }

    default AddressDto.VillageDto mapVillageDetail(Address entity) {
        if (entity.getVillageId() == null) return null;
        AddressDto.VillageDto dto = new AddressDto.VillageDto();
        dto.setId(entity.getVillageId());
        dto.setDistrictId(entity.getDistrictId());
        dto.setName(entity.getVillage());
        return dto;
    }
}
