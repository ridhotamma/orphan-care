package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.GuardianDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.service.GuardianService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/guardians")
@RequiredArgsConstructor
public class GuardianController {

    private final GuardianService guardianService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public GuardianDto.Response createGuardian(@Valid @RequestBody GuardianDto guardianDto) {
        return guardianService.createGuardian(guardianDto);
    }

    @PutMapping("/{guardianId}")
    @PreAuthorize("hasRole('ADMIN')")
    public GuardianDto.Response updateGuardian(@PathVariable UUID guardianId, @Valid @RequestBody GuardianDto guardianDto) {
        return guardianService.updateGuardian(guardianId, guardianDto);
    }

    @DeleteMapping("/{guardianId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGuardian(@PathVariable UUID guardianId) {
        guardianService.deleteGuardian(guardianId);
    }

    @GetMapping("/{guardianId}")
    @PreAuthorize("hasRole('ADMIN')")
    public GuardianDto.Response getGuardianById(@PathVariable UUID guardianId) {
        return guardianService.getGuardianById(guardianId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PaginatedResponse<List<GuardianDto.Response>>> getAllGuardians(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UUID guardianTypeId,
            @RequestParam(defaultValue = "fullName") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        PaginatedResponse<List<GuardianDto.Response>> guardians = guardianService.getAllGuardians(search, guardianTypeId, sortBy, sortOrder, page, perPage);
        return ResponseEntity.ok(guardians);
    }
}
