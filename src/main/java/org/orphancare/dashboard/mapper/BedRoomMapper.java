package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.orphancare.dashboard.dto.BedRoomDto;
import org.orphancare.dashboard.entity.BedRoom;

@Mapper(componentModel = "spring", uses = { ProfileMapper.class })
public interface BedRoomMapper {
    @Mapping(source = "bedRoom.bedRoomType.id", target = "bedRoomTypeId")
    @Mapping(target = "profiles", source = "profiles")
    BedRoomDto toDto(BedRoom bedRoom);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bedRoomType", ignore = true)
    @Mapping(target = "profiles", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BedRoom toEntity(BedRoomDto.CreateBedRoomDto createBedRoomDto);

    @Mapping(target = "bedRoomType", ignore = true)
    @Mapping(target = "profiles", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget BedRoom bedRoom, BedRoomDto.UpdateBedRoomDto updateBedRoomDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BedRoomDto.DropdownDto toDropdownDto(BedRoom bedRoom);
}
