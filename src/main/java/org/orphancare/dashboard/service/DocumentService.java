package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentRequestDto;
import org.orphancare.dashboard.dto.DocumentResponseDto;
import org.orphancare.dashboard.entity.Document;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.repository.DocumentRepository;
import org.orphancare.dashboard.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ProfileRepository profileRepository;

    public DocumentResponseDto addDocument(DocumentRequestDto documentRequestDto) {
        Profile profile = profileRepository.findById(documentRequestDto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for this id: " + documentRequestDto.getProfileId()));

        Document document = new Document();
        document.setId(UUID.randomUUID());
        document.setName(documentRequestDto.getName());
        document.setUrl(documentRequestDto.getUrl());
        document.setProfile(profile);
        document.setDocumentType(documentRequestDto.getDocumentType());

        Document savedDocument = documentRepository.save(document);

        return toResponseDto(savedDocument);
    }

    public DocumentResponseDto getDocument(UUID id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for this id: " + id));
        return toResponseDto(document);
    }

    public DocumentResponseDto updateDocument(UUID id, DocumentRequestDto documentRequestDto) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for this id: " + id));

        Profile profile = profileRepository.findById(documentRequestDto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for this id: " + documentRequestDto.getProfileId()));

        document.setName(documentRequestDto.getName());
        document.setUrl(documentRequestDto.getUrl());
        document.setProfile(profile);
        document.setDocumentType(documentRequestDto.getDocumentType());

        Document updatedDocument = documentRepository.save(document);

        return toResponseDto(updatedDocument);
    }

    public void deleteDocument(UUID id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for this id: " + id));
        documentRepository.delete(document);
    }

    private DocumentResponseDto toResponseDto(Document document) {
        return new DocumentResponseDto(
                document.getId(),
                document.getName(),
                document.getUrl(),
                document.getProfile().getId(),
                document.getDocumentType()
        );
    }
}
