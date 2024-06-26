package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.orphancare.dashboard.dto.*;
import org.orphancare.dashboard.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toEntity(CreateUserDto createUserDto);

    @Mapping(target = "password", ignore = true)
    void updateUserFromDto(UpdateUserDto updateUserDto, @MappingTarget User user);

    UserDto toDto(User user);

    @Mapping(source = "profile.fullName", target = "profile.fullName")
    @Mapping(source = "profile.profilePicture", target = "profile.profilePicture")
    @Mapping(source = "profile.phoneNumber", target = "profile.phoneNumber")
    UserDto.UserWithProfileDto toUserWithProfileDto(User user);

    @Mapping(source = "profile", target = "profile")
    UserDto.CurrentUserDto toCurrentUserDto(User user);
}
