package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.ProfileDto;
import org.orphancare.dashboard.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping("/{userId}")
    public ResponseEntity<ProfileDto> createOrUpdateProfile(
            @PathVariable UUID userId,
            @Valid @RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.createOrUpdateProfile(userId, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDto> getProfileByUserId(@PathVariable UUID userId) {
        ProfileDto profileDto = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profileDto);
    }
}
