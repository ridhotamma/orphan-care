package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.orphancare.dashboard.dto.ProfileDto;
import org.orphancare.dashboard.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(source = "bedRoom.id", target = "bedRoomId")
    @Mapping(source = "bedRoom", target = "bedRoom")
    ProfileDto toDto(Profile profile);

    @Mapping(source = "bedRoomId", target = "bedRoom.id")
    @Mapping(source = "bedRoom", target = "bedRoom")
    Profile toEntity(ProfileDto profileDto);
}
