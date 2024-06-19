package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.orphancare.dashboard.dto.BedRoomDto;
import org.orphancare.dashboard.entity.BedRoom;

@Mapper(componentModel = "spring")
public interface BedRoomMapper {
    BedRoomMapper INSTANCE = Mappers.getMapper(BedRoomMapper.class);

    @Mapping(target = "bedRoomType", source = "bedRoomType")
    BedRoomDto toDto(BedRoom bedRoom);

    @Mapping(target = "bedRoomType", expression = "java(org.orphancare.dashboard.entity.BedRoomType.valueOf(bedRoomDto.getBedRoomType().toUpperCase()))")
    BedRoom toEntity(BedRoomDto bedRoomDto);
}
