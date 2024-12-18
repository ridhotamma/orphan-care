package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.orphancare.dashboard.dto.ProfileDto;
import org.orphancare.dashboard.entity.Profile;

@Mapper(componentModel = "spring", uses = { AddressMapper.class, BedRoomMapper.class })
public interface ProfileMapper {
    @Mapping(source = "profile.bedRoom.id", target = "bedRoomId")
    @Mapping(source = "profile.bedRoom", target = "bedRoom")
    @Mapping(source = "profile.address", target = "address")
    @Mapping(source = "profile.guardian", target = "guardian")
    @Mapping(source = "profile.guardianRelationship", target = "guardianRelationship")
    @Mapping(source = "profile.guardianRelationship.id", target = "guardianTypeId")
    @Mapping(source = "profile.bedRoom.bedRoomType.id", target = "bedRoom.bedRoomTypeId")
    @Mapping(target = "orphanTypeText", expression = "java(profile.getOrphanTypeText())")
    ProfileDto toDto(Profile profile);

    @Mapping(source = "profileDto.bedRoomId", target = "bedRoom.id")
    @Mapping(source = "profileDto.bedRoom", target = "bedRoom")
    @Mapping(source = "profileDto.address", target = "address")
    @Mapping(source = "profileDto.guardian", target = "guardian")
    @Mapping(source = "profileDto.guardianTypeId", target = "guardianRelationship.id")
    Profile toEntity(ProfileDto profileDto);
}
