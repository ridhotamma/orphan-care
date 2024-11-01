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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaginatedResponse<List<BedRoomDto>>> getAllBedRooms(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID bedRoomTypeId,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        PaginatedResponse<List<BedRoomDto>> bedRooms = bedRoomService.getAllBedrooms(name, bedRoomTypeId, sortBy, sortOrder, page, perPage);
        return ResponseEntity.ok(bedRooms);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BedRoomDto> getBedRoomById(@PathVariable UUID id) {
        BedRoomDto bedRoom = bedRoomService.getBedRoomById(id);
        return ResponseEntity.ok(bedRoom);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BedRoomDto> createBedRoom(@RequestBody @Valid BedRoomDto.CreateBedRoomDto createBedRoomDto) {
        BedRoomDto createdBedRoom = bedRoomService.createBedRoom(createBedRoomDto);
        return ResponseEntity.status(201).body(createdBedRoom);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BedRoomDto> updateBedRoom(@PathVariable UUID id, @RequestBody @Valid BedRoomDto.UpdateBedRoomDto updateBedRoomDto) {
        BedRoomDto updatedBedRoom = bedRoomService.updateBedRoom(id, updateBedRoomDto);
        return ResponseEntity.ok(updatedBedRoom);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBedRoom(@PathVariable UUID id) {
        bedRoomService.deleteBedRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dropdown")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<BedRoomDto.DropdownDto>> getBedRoomListDropdown() {
        List<BedRoomDto.DropdownDto> bedRooms = bedRoomService.getBedRoomDropdownList();
        return ResponseEntity.ok(bedRooms);
    }
}
