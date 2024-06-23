package org.orphancare.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.GuardianDto;
import org.orphancare.dashboard.service.GuardianService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/guardians")
@RequiredArgsConstructor
public class GuardianController {

    private final GuardianService guardianService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GuardianDto createGuardian(@RequestBody GuardianDto guardianDto) {
        return guardianService.createGuardian(guardianDto);
    }

    @PutMapping("/{guardianId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GuardianDto updateGuardian(@PathVariable UUID guardianId, @RequestBody GuardianDto guardianDto) {
        return guardianService.updateGuardian(guardianId, guardianDto);
    }

    @DeleteMapping("/{guardianId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteGuardian(@PathVariable UUID guardianId) {
        guardianService.deleteGuardian(guardianId);
    }

    @GetMapping("/{guardianId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GuardianDto.Response getGuardianById(@PathVariable UUID guardianId) {
        return guardianService.getGuardianById(guardianId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<GuardianDto.Response> getAllGuardians() {
        return guardianService.getAllGuardians();
    }
}
