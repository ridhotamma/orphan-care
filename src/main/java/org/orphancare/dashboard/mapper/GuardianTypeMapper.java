package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.orphancare.dashboard.dto.GuardianTypeDto;
import org.orphancare.dashboard.entity.GuardianType;

@Mapper(componentModel = "spring")
public interface GuardianTypeMapper {
    GuardianTypeDto toDto(GuardianType guardianType);
    GuardianType toEntity(GuardianTypeDto guardianTypeDto);
}
