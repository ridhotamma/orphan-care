package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentDto;
import org.orphancare.dashboard.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/users")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/{userId}/documents")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<DocumentDto.Response> createDocument(
            @PathVariable UUID userId,
            @Valid @RequestBody DocumentDto documentDto) {
        DocumentDto.Response createdDocument = documentService.createDocument(userId, documentDto);
        return ResponseEntity.ok(createdDocument);
    }

    @PutMapping("/{userId}/documents/{documentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<DocumentDto> updateDocument(
            @PathVariable UUID userId,
            @PathVariable UUID documentId,
            @Valid @RequestBody DocumentDto documentDto) {
        documentDto.setDocumentTypeId(documentId);
        DocumentDto updatedDocument = documentService.updateDocument(documentId, documentDto);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/{userId}/documents/{documentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID userId, @PathVariable UUID documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/documents/{documentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<DocumentDto.Response> getDocumentById(@PathVariable UUID userId, @PathVariable UUID documentId) {
        DocumentDto.Response document = documentService.getDocumentById(documentId);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/{userId}/documents")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<DocumentDto.Response>> getAllDocumentsByUserId(@PathVariable UUID userId) {
        List<DocumentDto.Response> documents = documentService.getAllDocumentsByUserId(userId);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/documents")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<DocumentDto.Response>> getCurrentUserDocuments() {
        List<DocumentDto.Response> documents = documentService.getCurrentUserDocuments();
        return ResponseEntity.ok(documents);
    }
}
