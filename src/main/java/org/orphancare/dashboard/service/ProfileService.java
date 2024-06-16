package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.ProfileRequestDto;
import org.orphancare.dashboard.dto.ProfileResponseDto;
import org.orphancare.dashboard.entity.Document;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.repository.DocumentRepository;
import org.orphancare.dashboard.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final DocumentRepository documentRepository;

    public ProfileResponseDto updateProfile(UUID id, ProfileRequestDto profileDto) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for this id: " + id));

        profile.setFullName(profileDto.getFullName());
        profile.setBirthday(profileDto.getBirthday());
        profile.setProfilePicture(profileDto.getProfilePicture());
        profile.setBio(profileDto.getBio());
        profile.setAddress(profileDto.getAddress());
        profile.setSchoolGrade(profileDto.getSchoolGrade());
        profile.setSchoolType(profileDto.getSchoolType());

        Set<Document> documents = Optional.ofNullable(profileDto.getDocumentIds())
                .orElse(Collections.emptySet())
                .stream()
                .map(documentRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        profile.setDocuments(documents);

        Profile updatedProfile = profileRepository.save(profile);
        return toResponseDto(updatedProfile);
    }

    private ProfileResponseDto toResponseDto(Profile profile) {
        return new ProfileResponseDto(
                profile.getId(),
                profile.getProfilePicture(),
                profile.getFullName(),
                profile.getBirthday(),
                profile.getBio(),
                profile.getAddress(),
                profile.getDocuments(),
                profile.getSchoolGrade(),
                profile.getSchoolType()
        );
    }
}
