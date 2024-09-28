package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.UnitDto;
import org.orphancare.dashboard.service.UnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UnitDto>> getUnits() {
        List<UnitDto> units = unitService.getAllUnits();
        return ResponseEntity.ok(units);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UnitDto> getUnitById(@PathVariable UUID id) {
        UnitDto unit = unitService.getUnitById(id);
        return ResponseEntity.ok(unit);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UnitDto> createUnit(@RequestBody @Valid UnitDto unitDto) {
        UnitDto createdUnit = unitService.createUnit(unitDto);
        return ResponseEntity.status(201).body(createdUnit);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UnitDto> updateUnit(@PathVariable UUID id, @RequestBody @Valid UnitDto unitDto) {
        UnitDto updatedUnit = unitService.updateUnit(id, unitDto);
        return ResponseEntity.ok(updatedUnit);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUnit(@PathVariable UUID id) {
        unitService.deleteUnitById(id);
        return ResponseEntity.noContent().build();
    }
}