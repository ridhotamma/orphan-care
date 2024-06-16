package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<ProfileResponseDto> createProfile(@Valid @RequestBody ProfileRequestDto profileRequestDto) {
        ProfileResponseDto profileResponseDto = profileService.createProfile(profileRequestDto);
        return ResponseEntity.ok(profileResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable UUID id) {
        ProfileResponseDto profileResponseDto = profileService.getProfile(id);
        if (profileResponseDto != null) {
            return ResponseEntity.ok(profileResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseDto> updateProfile(@PathVariable UUID id, @Valid @RequestBody ProfileRequestDto profileRequestDto) {
        ProfileResponseDto profileResponseDto = profileService.updateProfile(id, profileRequestDto);
        if (profileResponseDto != null) {
            return ResponseEntity.ok(profileResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
