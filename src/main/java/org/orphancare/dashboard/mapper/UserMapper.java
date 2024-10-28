package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.orphancare.dashboard.dto.CreateUserDto;
import org.orphancare.dashboard.dto.UpdateUserDto;
import org.orphancare.dashboard.dto.UserDto;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.entity.User;

@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toEntity(CreateUserDto createUserDto);

    @Mapping(target = "password", ignore = true)
    void updateUserFromDto(UpdateUserDto updateUserDto, @MappingTarget User user);

    UserDto toDto(User user);

    @Mapping(source = "profile", target = "profile")
    @Mapping(source = "profile.bedRoom.bedRoomType.id", target = "profile.bedRoom.bedRoomTypeId")
    @Mapping(source = "profile.bedRoom.id", target = "profile.bedRoomId")
    @Mapping(source = "profile.guardian.guardianType.id", target = "profile.guardian.guardianTypeId")
    UserDto.UserWithProfileDto toUserWithProfileDto(User user);

    @Mapping(source = "profile.fullName", target = "fullName")
    @Mapping(source = "profile.profilePicture", target = "profilePicture")
    UserDto.DocumentOwnerDto toUserDocumentOwnerDto(User user);

    @Mapping(source = "createUserDto.fullName", target = "fullName")
    @Mapping(source = "createUserDto.profilePicture", target = "profilePicture")
    @Mapping(source = "createUserDto.birthday", target = "birthday")
    @Mapping(source = "createUserDto.joinDate", target = "joinDate")
    @Mapping(source = "createUserDto.bio", target = "bio")
    @Mapping(source = "createUserDto.phoneNumber", target = "phoneNumber")
    @Mapping(source = "createUserDto.gender", target = "gender")
    @Mapping(source = "createUserDto.careTaker", target = "careTaker")
    @Mapping(source = "createUserDto.alumni", target = "alumni")
    Profile toProfileEntity(CreateUserDto createUserDto);
}
