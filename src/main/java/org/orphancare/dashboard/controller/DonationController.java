package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DonationDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.service.DonationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginatedResponse<Page<DonationDto>>> getDonations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "receivedDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String search) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        PaginatedResponse<Page<DonationDto>> donations = donationService.getAllDonations(pageRequest, search);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DonationDto> getDonationById(@PathVariable UUID id) {
        DonationDto donation = donationService.getDonationById(id);
        return ResponseEntity.ok(donation);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DonationDto> createDonation(@RequestBody @Valid DonationDto donationDto) {
        DonationDto createdDonation = donationService.createDonation(donationDto);
        return ResponseEntity.status(201).body(createdDonation);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DonationDto> updateDonation(@PathVariable UUID id, @RequestBody @Valid DonationDto donationDto) {
        DonationDto updatedDonation = donationService.updateDonation(id, donationDto);
        return ResponseEntity.ok(updatedDonation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteDonation(@PathVariable UUID id) {
        donationService.deleteDonationById(id);
        return ResponseEntity.noContent().build();
    }
}