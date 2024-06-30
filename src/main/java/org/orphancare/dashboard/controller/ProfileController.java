package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.ProfileDto;
import org.orphancare.dashboard.dto.UserDto;
import org.orphancare.dashboard.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ProfileDto> createOrUpdateProfile(
            @PathVariable UUID userId,
            @Valid @RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.createOrUpdateProfile(userId, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ProfileDto> getProfileByUserId(@PathVariable UUID userId) {
        ProfileDto profileDto = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profileDto);
    }

    @GetMapping("/current-user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<UserDto.UserWithProfileDto> getCurrentUser() {
        UserDto.UserWithProfileDto updatedUser = profileService.getCurrentUserWithProfile();
        return ResponseEntity.ok(updatedUser);
    }
}
