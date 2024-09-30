package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.orphancare.dashboard.dto.BedRoomDto;
import org.orphancare.dashboard.entity.BedRoom;

@Mapper(componentModel = "spring")
public interface BedRoomMapper {
    @Mapping(source = "bedRoom.bedRoomType.id", target = "bedRoomTypeId")
    @Mapping(target = "profiles", source = "profiles")
    BedRoomDto toDto(BedRoom bedRoom);

    @Mapping(target = "bedRoomType", ignore = true)
    @Mapping(target = "profiles", ignore = true)
    BedRoom toEntity(BedRoomDto bedRoomDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BedRoomDto.DropdownDto toDropdownDto(BedRoom bedRoom);
}
