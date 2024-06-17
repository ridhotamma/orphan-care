package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentDto;
import org.orphancare.dashboard.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users/{userId}/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentDto> createDocument(
            @PathVariable UUID userId,
            @Valid @RequestBody DocumentDto documentDto) {
        DocumentDto createdDocument = documentService.createDocument(userId, documentDto);
        return ResponseEntity.ok(createdDocument);
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<DocumentDto> updateDocument(
            @PathVariable UUID userId,
            @PathVariable UUID documentId,
            @Valid @RequestBody DocumentDto documentDto) {
        documentDto.setDocumentTypeId(documentId);
        DocumentDto updatedDocument = documentService.updateDocument(documentId, documentDto);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID userId, @PathVariable UUID documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentDto> getDocumentById(@PathVariable UUID userId, @PathVariable UUID documentId) {
        DocumentDto document = documentService.getDocumentById(documentId);
        return ResponseEntity.ok(document);
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getAllDocumentsByUserId(@PathVariable UUID userId) {
        List<DocumentDto> documents = documentService.getAllDocumentsByUserId(userId);
        return ResponseEntity.ok(documents);
    }
}
