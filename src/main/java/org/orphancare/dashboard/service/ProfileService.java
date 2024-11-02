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

        BedRoom bedRoom = null;
        if (profileDto.getBedRoomId() != null) {
            bedRoom = bedRoomRepository.findById(profileDto.getBedRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bed room not found with id " + profileDto.getBedRoomId()));
        }

        GuardianType guardianRelationship = null;
        if (profileDto.getGuardianTypeId() != null) {
            guardianRelationship = guardianTypeRepository.findById(profileDto.getGuardianTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Guardian type not found with id " + profileDto.getGuardianTypeId()));
        }

        Guardian guardian;
        if (profileDto.getGuardian() != null) {
            if (profileDto.getGuardian().getId() != null) {
                guardian = guardianRepository.findById(profileDto.getGuardian().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Guardian not found with id " + profileDto.getGuardian().getId()));

                guardian.setFullName(profileDto.getGuardian().getFullName());
                guardian.setPhoneNumber(profileDto.getGuardian().getPhoneNumber());

                if (profileDto.getGuardian().getAddress() != null) {
                    Address guardianAddress = addressMapper.toEntity(profileDto.getGuardian().getAddress());
                    guardian.setAddress(guardianAddress);
                }
            } else {
                guardian = guardianMapper.toEntityFromResponse(profileDto.getGuardian());
                if (profileDto.getGuardian().getAddress() != null) {
                    Address guardianAddress = addressMapper.toEntity(profileDto.getGuardian().getAddress());
                    guardian.setAddress(guardianAddress);
                }
            }
            guardian = guardianRepository.save(guardian);
        } else {
            guardian = null;
        }

        Profile profile = profileRepository.findByUser(user).orElse(new Profile());

        profile.setFullName(profileDto.getFullName());
        profile.setProfilePicture(profileDto.getProfilePicture());
        profile.setBirthday(profileDto.getBirthday());
        profile.setJoinDate(profileDto.getJoinDate());
        profile.setLeaveDate(profileDto.getLeaveDate());
        profile.setBio(profileDto.getBio());
        profile.setPhoneNumber(profileDto.getPhoneNumber());
        profile.setGender(Gender.valueOf(profileDto.getGender().toUpperCase()));
        profile.setUser(user);

        if (profileDto.getAddress() != null) {
            Address profileAddress = addressMapper.toEntity(profileDto.getAddress());
            profile.setAddress(profileAddress);
        }

        profile.setBedRoom(bedRoom);
        profile.setCareTaker(profileDto.isCareTaker());
        profile.setAlumni(profileDto.isAlumni());
        profile.setNikNumber(profileDto.getNikNumber());
        profile.setKkNumber(profileDto.getKkNumber());
        profile.setOrphanType(profileDto.getOrphanType());
        profile.setGuardianRelationship(guardianRelationship);
        profile.setGuardian(guardian);

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
