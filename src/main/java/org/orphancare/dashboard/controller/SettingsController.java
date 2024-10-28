package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.SettingsDto;
import org.orphancare.dashboard.service.SettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SettingsDto> getSettings() {
        SettingsDto settings = settingsService.getSettings();
        return ResponseEntity.ok(settings);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SettingsDto> updateSettings(@RequestBody @Valid SettingsDto settingsDto) {
        SettingsDto updatedSettings = settingsService.updateSettings(settingsDto);
        return ResponseEntity.ok(updatedSettings);
    }
}
