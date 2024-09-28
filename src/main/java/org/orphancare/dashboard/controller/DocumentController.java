package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/users")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/{userId}/documents")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DocumentDto.Response> createDocument(
            @PathVariable UUID userId,
            @Valid @RequestBody DocumentDto documentDto) {
        DocumentDto.Response createdDocument = documentService.createDocument(userId, documentDto);
        return ResponseEntity.ok(createdDocument);
    }

    @PutMapping("/{userId}/documents/{documentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DocumentDto> updateDocument(
            @PathVariable UUID userId,
            @PathVariable UUID documentId,
            @Valid @RequestBody DocumentDto documentDto) {
        documentDto.setDocumentTypeId(documentId);
        DocumentDto updatedDocument = documentService.updateDocument(documentId, documentDto);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/{userId}/documents/{documentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID userId, @PathVariable UUID documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/documents/{documentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DocumentDto.Response> getDocumentById(@PathVariable UUID userId, @PathVariable UUID documentId) {
        DocumentDto.Response document = documentService.getDocumentById(documentId);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/{userId}/documents")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PaginatedResponse<List<DocumentDto.Response>>> getAllDocumentsByUserId(
            @PathVariable UUID userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID documentTypeId,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        PaginatedResponse<List<DocumentDto.Response>> documents = documentService.getAllDocumentsByUserId(userId, name, documentTypeId, sortBy, sortOrder, page, perPage);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/{userId}/documents-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<DocumentDto.GroupByDateRange>> getDocumentsGroupedByDate(@PathVariable UUID userId) {
        List<DocumentDto.GroupByDateRange> groupedDocuments = documentService.getDocumentsGroupedByDate(userId);
        return ResponseEntity.ok(groupedDocuments);
    }

    @GetMapping("/documents")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PaginatedResponse<List<DocumentDto.Response>>> getCurrentUserDocuments(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID documentTypeId,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        PaginatedResponse<List<DocumentDto.Response>> documents = documentService.getCurrentUserDocuments(name, documentTypeId, sortBy, sortOrder, page, perPage);
        return ResponseEntity.ok(documents);
    }
}
