package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.AddressDto;
import org.orphancare.dashboard.dto.ProfileDto;
import org.orphancare.dashboard.entity.Address;
import org.orphancare.dashboard.entity.Gender;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.repository.ProfileRepository;
import org.orphancare.dashboard.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileDto createOrUpdateProfile(UUID userId, ProfileDto profileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

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
            Address address = profile.getAddress() != null ? profile.getAddress() : new Address();
            updateAddressEntity(profileDto.getAddress(), address);
            profile.setAddress(address);
        }

        Profile savedProfile = profileRepository.save(profile);
        return convertToDto(savedProfile);
    }

    public ProfileDto getProfileByUserId(UUID userId) {
        Profile profile = profileRepository.findByUserId(userId).orElse(new Profile());

        return convertToDto(profile);
    }

    private void updateAddressEntity(AddressDto addressDto, Address address) {
        address.setStreet(addressDto.getStreet());
        address.setUrbanVillage(addressDto.getUrbanVillage());
        address.setSubdistrict(addressDto.getSubdistrict());
        address.setCity(addressDto.getCity());
        address.setProvince(addressDto.getProvince());
        address.setPostalCode(addressDto.getPostalCode());
    }

    private ProfileDto convertToDto(Profile profile) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setFullName(profile.getFullName());
        profileDto.setProfilePicture(profile.getProfilePicture());
        profileDto.setBirthday(profile.getBirthday());
        profileDto.setJoinDate(profile.getJoinDate());
        profileDto.setLeaveDate(profile.getLeaveDate());
        profileDto.setBio(profile.getBio());
        profileDto.setPhoneNumber(profile.getPhoneNumber());

        if (profile.getGender() != null) {
            profileDto.setGender(profile.getGender().name());
        }

        if (profile.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(profile.getAddress().getStreet());
            addressDto.setUrbanVillage(profile.getAddress().getUrbanVillage());
            addressDto.setSubdistrict(profile.getAddress().getSubdistrict());
            addressDto.setCity(profile.getAddress().getCity());
            addressDto.setProvince(profile.getAddress().getProvince());
            addressDto.setPostalCode(profile.getAddress().getPostalCode());
            profileDto.setAddress(addressDto);
        }

        return profileDto;
    }
}
