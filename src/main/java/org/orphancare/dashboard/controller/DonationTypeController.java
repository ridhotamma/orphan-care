package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DonationTypeDto;
import org.orphancare.dashboard.service.DonationTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/donation-types")
@RequiredArgsConstructor
public class DonationTypeController {

    private final DonationTypeService donationTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DonationTypeDto>> getDonationTypes() {
        List<DonationTypeDto> donationTypes = donationTypeService.getAllDonationTypes();
        return ResponseEntity.ok(donationTypes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonationTypeDto> getDonationTypeById(@PathVariable UUID id) {
        DonationTypeDto donationType = donationTypeService.getDonationTypeById(id);
        return ResponseEntity.ok(donationType);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonationTypeDto> createDonationType(@RequestBody @Valid DonationTypeDto donationTypeDto) {
        DonationTypeDto createdDonationType = donationTypeService.createDonationType(donationTypeDto);
        return ResponseEntity.status(201).body(createdDonationType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonationTypeDto> updateDonationType(@PathVariable UUID id, @RequestBody @Valid DonationTypeDto donationTypeDto) {
        DonationTypeDto updatedDonationType = donationTypeService.updateDonationType(id, donationTypeDto);
        return ResponseEntity.ok(updatedDonationType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDonationType(@PathVariable UUID id) {
        donationTypeService.deleteDonationTypeById(id);
        return ResponseEntity.noContent().build();
    }
}
