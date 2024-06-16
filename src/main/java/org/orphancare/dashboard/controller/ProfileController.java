package org.orphancare.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.ProfileRequestDto;
import org.orphancare.dashboard.dto.ProfileResponseDto;
import org.orphancare.dashboard.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseDto> updateProfile(@PathVariable UUID id, @RequestBody ProfileRequestDto profileDto) {
        ProfileResponseDto updatedProfile = profileService.updateProfile(id, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }

}
