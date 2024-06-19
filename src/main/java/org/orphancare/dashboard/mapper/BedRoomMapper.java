package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.orphancare.dashboard.dto.BedRoomDto;
import org.orphancare.dashboard.entity.BedRoom;

@Mapper(componentModel = "spring")
public interface BedRoomMapper {
    @Mapping(source = "bedRoom.bedRoomType.id", target = "bedRoomTypeId")
    BedRoomDto toDto(BedRoom bedRoom);

    BedRoom toEntity(BedRoomDto bedRoomDto);
}
