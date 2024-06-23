package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentTypeDto;
import org.orphancare.dashboard.service.DocumentTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/document-types")
@RequiredArgsConstructor
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DocumentTypeDto> createDocumentType(@Valid @RequestBody DocumentTypeDto documentTypeDto) {
        DocumentTypeDto createdDocumentType = documentTypeService.createDocumentType(documentTypeDto);
        return ResponseEntity.ok(createdDocumentType);
    }

    @PutMapping("/{documentTypeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DocumentTypeDto> updateDocumentType(
            @PathVariable UUID documentTypeId,
            @Valid @RequestBody DocumentTypeDto documentTypeDto) {
        DocumentTypeDto updatedDocumentType = documentTypeService.updateDocumentType(documentTypeId, documentTypeDto);
        return ResponseEntity.ok(updatedDocumentType);
    }

    @DeleteMapping("/{documentTypeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable UUID documentTypeId) {
        documentTypeService.deleteDocumentType(documentTypeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{documentTypeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DocumentTypeDto> getDocumentTypeById(@PathVariable UUID documentTypeId) {
        DocumentTypeDto documentType = documentTypeService.getDocumentTypeById(documentTypeId);
        return ResponseEntity.ok(documentType);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DocumentTypeDto>> getAllDocumentTypes() {
        List<DocumentTypeDto> documentTypes = documentTypeService.getAllDocumentTypes();
        return ResponseEntity.ok(documentTypes);
    }
}
