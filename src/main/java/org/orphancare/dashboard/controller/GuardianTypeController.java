package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.GuardianTypeDto;
import org.orphancare.dashboard.service.GuardianTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/guardian-types")
@RequiredArgsConstructor
public class GuardianTypeController {

    private final GuardianTypeService guardianTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GuardianTypeDto>> getAllGuardianTypes() {
        List<GuardianTypeDto> guardianTypeDtos = guardianTypeService.getAllGuardianTypes();
        return ResponseEntity.ok(guardianTypeDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GuardianTypeDto> getGuardianTypeById(@PathVariable UUID guardianTypeId) {
        GuardianTypeDto guardianTypeDto = guardianTypeService.getGuardianTypeById(guardianTypeId);
        return ResponseEntity.ok(guardianTypeDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GuardianTypeDto> createGuardianType(@RequestBody @Valid GuardianTypeDto guardianTypeDto) {
        GuardianTypeDto createdGuardianType = guardianTypeService.createGuardianType(guardianTypeDto);
        return ResponseEntity.ok(createdGuardianType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GuardianTypeDto> updateGuardianType(@PathVariable UUID id, @RequestBody @Valid GuardianTypeDto guardianTypeDto) {
        GuardianTypeDto updatedGuardianType = guardianTypeService.updateGuardianType(id, guardianTypeDto);
        return ResponseEntity.ok(updatedGuardianType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGuardianType(@PathVariable UUID id) {
        guardianTypeService.deleteGuardianTypeById(id);
        return ResponseEntity.noContent().build();
    }
}
