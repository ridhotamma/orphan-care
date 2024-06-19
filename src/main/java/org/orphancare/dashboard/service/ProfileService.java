package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.ProfileDto;
import org.orphancare.dashboard.entity.*;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.AddressMapper;
import org.orphancare.dashboard.mapper.ProfileMapper;
import org.orphancare.dashboard.repository.BedRoomRepository;
import org.orphancare.dashboard.repository.ProfileRepository;
import org.orphancare.dashboard.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final BedRoomRepository bedRoomRepository;
    private final ProfileMapper profileMapper;
    private final AddressMapper addressMapper;

    public ProfileDto createOrUpdateProfile(UUID userId, ProfileDto profileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        BedRoom bedRoom = bedRoomRepository.findById(profileDto.getBedRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Bed room not found with id " + profileDto.getBedRoomId()));

        Profile profile = profileRepository.findByUser(user).orElse(new Profile());
        profile.setFullName(profileDto.getFullName());
        profile.setProfilePicture(profileDto.getProfilePicture());
        profile.setBirthday(profileDto.getBirthday());
        profile.setJoinDate(profileDto.getJoinDate());
        profile.setLeaveDate(profileDto.getLeaveDate());
        profile.setBio(profileDto.getBio());
        profile.setPhoneNumber(profileDto.getPhoneNumber());
        profile.setGender(Gender.valueOf(profileDto.getGender().toUpperCase()));
        profile.setBedRoom(bedRoom);
        profile.setUser(user);
        profile.setAddress(addressMapper.toEntity(profileDto.getAddress()));

        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }

    public ProfileDto getProfileByUserId(UUID userId) {
        Profile profile = profileRepository.findByUserId(userId).orElse(new Profile());
        return profileMapper.toDto(profile);
    }
}
