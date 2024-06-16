package org.orphancare.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.entity.DocumentType;
import org.orphancare.dashboard.service.DocumentTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/document-types")
@RequiredArgsConstructor
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @GetMapping
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentType> getDocumentTypeById(@PathVariable UUID id) {
        Optional<DocumentType> documentType = documentTypeService.findById(id);
        return documentType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DocumentType createDocumentType(@RequestBody DocumentType documentType) {
        return documentTypeService.save(documentType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentType> updateDocumentType(@PathVariable UUID id, @RequestBody DocumentType documentType) {
        Optional<DocumentType> existingDocumentType = documentTypeService.findById(id);
        if (existingDocumentType.isPresent()) {
            documentType.setId(id);
            return ResponseEntity.ok(documentTypeService.save(documentType));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable UUID id) {
        documentTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
