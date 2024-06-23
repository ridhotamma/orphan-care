package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.orphancare.dashboard.dto.BedRoomTypeDto;
import org.orphancare.dashboard.dto.GuardianDto;
import org.orphancare.dashboard.entity.BedRoomType;
import org.orphancare.dashboard.entity.Guardian;

@Mapper(componentModel = "spring")
public interface GuardianMapper {
    BedRoomTypeDto toDto(Guardian guardian);
    BedRoomType toEntity(GuardianDto guardianDto);
}
