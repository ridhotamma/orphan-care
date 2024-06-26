package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.orphancare.dashboard.dto.GuardianDto;
import org.orphancare.dashboard.entity.Guardian;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, GuardianTypeMapper.class})
public interface GuardianMapper {
    @Mapping(source = "guardianType.id", target = "guardianTypeId")
    GuardianDto toDto(Guardian guardian);

    @Mapping(source = "guardianTypeId", target = "guardianType.id")
    Guardian toEntity(GuardianDto guardianDto);

    @Mapping(source = "guardianType", target = "guardianType")
    @Mapping(source = "address", target = "address")
    GuardianDto.Response toResponseDto(Guardian guardian);

    void updateGuardianFromDto(GuardianDto guardianDto, @MappingTarget Guardian guardian);
}
