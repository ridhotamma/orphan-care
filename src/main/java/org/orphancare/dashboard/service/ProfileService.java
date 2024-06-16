package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.ProfileRequestDto;
import org.orphancare.dashboard.dto.ProfileResponseDto;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final DocumentService documentService;

    public ProfileResponseDto createProfile(ProfileRequestDto profileRequestDto) {
        Profile profile = new Profile();
        profile.setProfilePicture(profileRequestDto.getProfilePicture());
        profile.setBio(profileRequestDto.getBio());
        profile.setSchoolGrade(profileRequestDto.getSchoolGrade());
        profile.setSchoolType(profileRequestDto.getSchoolType());
        profile.setAddress(profileRequestDto.getAddress());

        if (profileRequestDto.getDocumentIds() != null) {
            profile.setDocuments(profileRequestDto.getDocumentIds().stream()
                    .map(documentService::findById)
                    .collect(Collectors.toSet()));
        }

        Profile savedProfile = profileRepository.save(profile);
        return toResponseDto(savedProfile);
    }

    public ProfileResponseDto getProfile(UUID id) {
        Optional<Profile> profile = profileRepository.findById(id);
        return profile.map(this::toResponseDto).orElse(null);
    }

    public ProfileResponseDto updateProfile(UUID id, ProfileRequestDto profileRequestDto) {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setProfilePicture(profileRequestDto.getProfilePicture());
            profile.setBio(profileRequestDto.getBio());
            profile.setSchoolGrade(profileRequestDto.getSchoolGrade());
            profile.setSchoolType(profileRequestDto.getSchoolType());
            profile.setAddress(profileRequestDto.getAddress());

            if (profileRequestDto.getDocumentIds() != null) {
                profile.setDocuments(profileRequestDto.getDocumentIds().stream()
                        .map(documentService::findById)
                        .collect(Collectors.toSet()));
            }

            Profile updatedProfile = profileRepository.save(profile);
            return toResponseDto(updatedProfile);
        }
        return null;
    }

    public void deleteProfile(UUID id) {
        profileRepository.deleteById(id);
    }

    private ProfileResponseDto toResponseDto(Profile profile) {
        return new ProfileResponseDto(
                profile.getId(),
                profile.getProfilePicture(),
                profile.getBio(),
                profile.getAddress(),
                profile.getDocuments(),
                profile.getSchoolGrade(),
                profile.getSchoolType()
        );
    }
}
