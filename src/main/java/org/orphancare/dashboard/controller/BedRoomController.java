package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.BedRoomDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.service.BedRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/bedrooms")
@RequiredArgsConstructor
public class BedRoomController {

    private final BedRoomService bedRoomService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginatedResponse<List<BedRoomDto>>> getAllBedRooms(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        PaginatedResponse<List<BedRoomDto>> bedRooms = bedRoomService.getAllBedrooms(name, page, perPage);
        return ResponseEntity.ok(bedRooms);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BedRoomDto> getBedRoomById(@PathVariable UUID id) {
        BedRoomDto bedRoom = bedRoomService.getBedRoomById(id);
        return ResponseEntity.ok(bedRoom);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BedRoomDto> createBedRoom(@RequestBody @Valid BedRoomDto bedRoomDto) {
        BedRoomDto createdBedRoom = bedRoomService.createBedRoom(bedRoomDto);
        return ResponseEntity.status(201).body(createdBedRoom);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BedRoomDto> updateBedRoom(@PathVariable UUID id, @RequestBody @Valid BedRoomDto bedRoomDto) {
        BedRoomDto updatedBedRoom = bedRoomService.updateBedRoom(id, bedRoomDto);
        return ResponseEntity.ok(updatedBedRoom);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteBedRoom(@PathVariable UUID id) {
        bedRoomService.deleteBedRoom(id);
        return ResponseEntity.noContent().build();
    }
}
