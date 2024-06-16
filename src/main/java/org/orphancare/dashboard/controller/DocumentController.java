package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentRequestDto;
import org.orphancare.dashboard.dto.DocumentResponseDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.service.DocumentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    public PaginatedResponse<DocumentResponseDto> getAllDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return documentService.getAllDocuments(page, size);
    }

    @PostMapping
    public DocumentResponseDto addDocument(@RequestBody @Valid DocumentRequestDto documentRequestDto) {
        return documentService.addDocument(documentRequestDto);
    }

    @GetMapping("/{id}")
    public DocumentResponseDto getDocument(@PathVariable UUID id) {
        return documentService.getDocument(id);
    }

    @PutMapping("/{id}")
    public DocumentResponseDto updateDocument(@PathVariable UUID id, @RequestBody @Valid DocumentRequestDto documentRequestDto) {
        return documentService.updateDocument(id, documentRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable UUID id) {
        documentService.deleteDocument(id);
    }
}
