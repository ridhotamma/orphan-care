package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.orphancare.dashboard.dto.BedRoomTypeDto;
import org.orphancare.dashboard.entity.BedRoomType;

@Mapper(componentModel = "spring")
public interface BedRoomTypeMapper {
    BedRoomTypeDto toDto(BedRoomType bedRoom);
    BedRoomType toEntity(BedRoomTypeDto bedRoomDto);
}
