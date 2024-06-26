package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.InventoryTypeDto;
import org.orphancare.dashboard.service.InventoryTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/inventory-types")
@RequiredArgsConstructor
public class InventoryTypeController {

    private final InventoryTypeService inventoryTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<InventoryTypeDto>> getAllInventoryTypes() {
        List<InventoryTypeDto> inventoryTypes = inventoryTypeService.getAllInventoryTypes();
        return ResponseEntity.ok(inventoryTypes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<InventoryTypeDto> getInventoryTypeById(@PathVariable UUID id) {
        InventoryTypeDto inventoryType = inventoryTypeService.getInventoryTypeById(id);
        return ResponseEntity.ok(inventoryType);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<InventoryTypeDto> createInventoryType(@RequestBody @Valid InventoryTypeDto inventoryTypeDto) {
        InventoryTypeDto createdInventoryType = inventoryTypeService.createInventoryType(inventoryTypeDto);
        return ResponseEntity.status(201).body(createdInventoryType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<InventoryTypeDto> updateInventoryType(@PathVariable UUID id, @RequestBody @Valid InventoryTypeDto inventoryTypeDto) {
        InventoryTypeDto updatedInventoryType = inventoryTypeService.updateInventoryType(id, inventoryTypeDto);
        return ResponseEntity.ok(updatedInventoryType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteInventoryType(@PathVariable UUID id) {
        inventoryTypeService.deleteInventoryTypeById(id);
        return ResponseEntity.noContent().build();
    }
}
