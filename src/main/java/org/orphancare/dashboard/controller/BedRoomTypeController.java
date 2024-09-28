package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.BedRoomTypeDto;
import org.orphancare.dashboard.service.BedRoomTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/bedroom-types")
@RequiredArgsConstructor
public class BedRoomTypeController {

    private final BedRoomTypeService bedRoomTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BedRoomTypeDto>> getBedRoomTypes() {
        List<BedRoomTypeDto> bedRoomTypes = bedRoomTypeService.getAllBedRoomTypes();
        return ResponseEntity.ok(bedRoomTypes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BedRoomTypeDto> getBedRoomTypeById(@PathVariable UUID id) {
        BedRoomTypeDto bedRoom = bedRoomTypeService.getBedRoomTypeById(id);
        return ResponseEntity.ok(bedRoom);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BedRoomTypeDto> createBedRoomType(@RequestBody @Valid BedRoomTypeDto bedRoomTypeDto) {
        BedRoomTypeDto createdBedRoomType = bedRoomTypeService.createBedRoomType(bedRoomTypeDto);
        return ResponseEntity.status(201).body(createdBedRoomType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BedRoomTypeDto> updateBedRoomType(@PathVariable UUID id, @RequestBody @Valid BedRoomTypeDto bedRoomTypeDto) {
        BedRoomTypeDto updatedBedRoomType = bedRoomTypeService.updateBedRoomType(id, bedRoomTypeDto);
        return ResponseEntity.ok(updatedBedRoomType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBedRoomType(@PathVariable UUID id) {
        bedRoomTypeService.deleteBedRoomTypeById(id);
        return ResponseEntity.noContent().build();
    }
}
