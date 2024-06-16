package org.orphancare.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentRequestDto;
import org.orphancare.dashboard.dto.DocumentResponseDto;
import org.orphancare.dashboard.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentResponseDto> createDocument(@RequestBody DocumentRequestDto documentRequestDto) {
        DocumentResponseDto documentResponseDto = documentService.createDocument(documentRequestDto);
        return ResponseEntity.ok(documentResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponseDto> getDocument(@PathVariable UUID id) {
        DocumentResponseDto documentResponseDto = documentService.getDocument(id);
        if (documentResponseDto != null) {
            return ResponseEntity.ok(documentResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentResponseDto> updateDocument(@PathVariable UUID id, @RequestBody DocumentRequestDto documentRequestDto) {
        DocumentResponseDto documentResponseDto = documentService.updateDocument(id, documentRequestDto);
        if (documentResponseDto != null) {
            return ResponseEntity.ok(documentResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
