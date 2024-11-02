package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.ProfileDto;
import org.orphancare.dashboard.dto.UserDto;
import org.orphancare.dashboard.entity.*;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.AddressMapper;
import org.orphancare.dashboard.mapper.GuardianMapper;
import org.orphancare.dashboard.mapper.ProfileMapper;
import org.orphancare.dashboard.mapper.UserMapper;
import org.orphancare.dashboard.repository.*;
import org.orphancare.dashboard.util.RequestUtil;
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
    private final UserMapper userMapper;
    private final RequestUtil requestUtil;
    private final GuardianMapper guardianMapper;
    private final GuardianRepository guardianRepository;
    private final GuardianTypeRepository guardianTypeRepository;

    public ProfileDto createOrUpdateProfile(UUID userId, ProfileDto profileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Profile profile = profileRepository.findByUser(user).orElse(new Profile());

        if (profileDto.getBedRoomId() != null) {
            BedRoom bedRoom = bedRoomRepository.findById(profileDto.getBedRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bed room not found with id " + profileDto.getBedRoomId()));
            profile.setBedRoom(bedRoom);
        }

        if (profileDto.getGuardianTypeId() != null) {
            GuardianType guardianRelationship = guardianTypeRepository.findById(profileDto.getGuardianTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Guardian type not found with id " + profileDto.getGuardianTypeId()));
            profile.setGuardianRelationship(guardianRelationship);
        }

        if (profileDto.getGuardian() != null) {
            Guardian guardian;
            if (profileDto.getGuardian().getId() != null) {
                guardian = guardianRepository.findById(profileDto.getGuardian().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Guardian not found with id " + profileDto.getGuardian().getId()));

                if (profileDto.getGuardian().getFullName() != null) {
                    guardian.setFullName(profileDto.getGuardian().getFullName());
                }
                if (profileDto.getGuardian().getPhoneNumber() != null) {
                    guardian.setPhoneNumber(profileDto.getGuardian().getPhoneNumber());
                }
            } else {
                guardian = guardianMapper.toEntityFromResponse(profileDto.getGuardian());
            }

            if (profileDto.getGuardian().getAddress() != null) {
                Address guardianAddress = addressMapper.toEntity(profileDto.getGuardian().getAddress());
                guardian.setAddress(guardianAddress);
            }

            guardian = guardianRepository.save(guardian);
            profile.setGuardian(guardian);
        }

        if (profileDto.getFullName() != null) {
            profile.setFullName(profileDto.getFullName());
        }
        if (profileDto.getProfilePicture() != null) {
            profile.setProfilePicture(profileDto.getProfilePicture());
        }
        if (profileDto.getBirthday() != null) {
            profile.setBirthday(profileDto.getBirthday());
        }
        if (profileDto.getJoinDate() != null) {
            profile.setJoinDate(profileDto.getJoinDate());
        }
        if (profileDto.getLeaveDate() != null) {
            profile.setLeaveDate(profileDto.getLeaveDate());
        }
        if (profileDto.getBio() != null) {
            profile.setBio(profileDto.getBio());
        }
        if (profileDto.getPhoneNumber() != null) {
            profile.setPhoneNumber(profileDto.getPhoneNumber());
        }
        if (profileDto.getGender() != null) {
            profile.setGender(Gender.valueOf(profileDto.getGender().toUpperCase()));
        }
        if (profileDto.getNikNumber() != null) {
            profile.setNikNumber(profileDto.getNikNumber());
        }
        if (profileDto.getKkNumber() != null) {
            profile.setKkNumber(profileDto.getKkNumber());
        }
        if (profileDto.getOrphanType() != null) {
            profile.setOrphanType(profileDto.getOrphanType());
        }

        profile.setCareTaker(profileDto.isCareTaker());
        profile.setAlumni(profileDto.isAlumni());

        profile.setUser(user);

        if (profileDto.getAddress() != null) {
            Address profileAddress = addressMapper.toEntity(profileDto.getAddress());
            profile.setAddress(profileAddress);
        }

        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }

    public ProfileDto getProfileByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        Profile profile = profileRepository.findByUserId(userId).orElse(new Profile());
        return profileMapper.toDto(profile);
    }

    public UserDto.UserWithProfileDto getCurrentUserWithProfile() {
        String currentUsername = requestUtil.getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + currentUsername));
        return userMapper.toUserWithProfileDto(user);
    }
}
