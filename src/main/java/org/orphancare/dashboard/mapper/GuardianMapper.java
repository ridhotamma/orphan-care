package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.orphancare.dashboard.dto.GuardianDto;
import org.orphancare.dashboard.entity.Guardian;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, GuardianTypeMapper.class})
public interface GuardianMapper {
    GuardianDto toDto(Guardian guardian);

    Guardian toEntity(GuardianDto guardianDto);

    @Mapping(target = "id", ignore = true)
    Guardian toEntityFromResponse(GuardianDto.Response guardianDto);

    @Mapping(source = "address", target = "address")
    GuardianDto.Response toResponseDto(Guardian guardian);

    void updateGuardianFromDto(GuardianDto guardianDto, @MappingTarget Guardian guardian);
}
