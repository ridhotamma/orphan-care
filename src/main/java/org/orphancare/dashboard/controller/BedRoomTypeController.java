package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.BedRoomTypeDto;
import org.orphancare.dashboard.service.BedRoomTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/bedroom-types")
@RequiredArgsConstructor
public class BedRoomTypeController {

    private final BedRoomTypeService bedRoomTypeService;

    @GetMapping
    public ResponseEntity<List<BedRoomTypeDto>> getBedRoomTypes() {
        List<BedRoomTypeDto> bedRoomTypes = bedRoomTypeService.getAllBedRoomTypes();
        return ResponseEntity.ok(bedRoomTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BedRoomTypeDto> getBedRoomTypeById(@PathVariable UUID id) {
        BedRoomTypeDto bedRoom = bedRoomTypeService.getBedRoomTypeById(id);
        return ResponseEntity.ok(bedRoom);
    }

    @PostMapping
    public ResponseEntity<BedRoomTypeDto> createBedRoomType(@RequestBody @Valid BedRoomTypeDto bedRoomTypeDto) {
        BedRoomTypeDto createdBedRoomType = bedRoomTypeService.createBedRoomType(bedRoomTypeDto);
        return ResponseEntity.status(201).body(createdBedRoomType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BedRoomTypeDto> updateBedRoomType(@PathVariable UUID id, @RequestBody @Valid BedRoomTypeDto bedRoomTypeDto) {
        BedRoomTypeDto updatedBedRoomType = bedRoomTypeService.updateBedRoomType(id, bedRoomTypeDto);
        return ResponseEntity.ok(updatedBedRoomType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBedRoomType(@PathVariable UUID id) {
        bedRoomTypeService.deleteBedRoomTypeById(id);
        return ResponseEntity.noContent().build();
    }
}
