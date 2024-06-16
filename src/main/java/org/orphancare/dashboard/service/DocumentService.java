package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentRequestDto;
import org.orphancare.dashboard.dto.DocumentResponseDto;
import org.orphancare.dashboard.entity.Document;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.repository.DocumentRepository;
import org.orphancare.dashboard.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ProfileRepository profileRepository;

    public DocumentResponseDto createDocument(DocumentRequestDto documentRequestDto) {
        Document document = new Document();
        document.setName(documentRequestDto.getName());
        document.setUrl(documentRequestDto.getUrl());
        document.setDocumentType(documentRequestDto.getDocumentType());

        Optional<Profile> profileOptional = profileRepository.findById(documentRequestDto.getProfileId());
        if (profileOptional.isPresent()) {
            document.setProfile(profileOptional.get());
        } else {
            throw new IllegalArgumentException("Profile not found");
        }

        Document savedDocument = documentRepository.save(document);
        return toResponseDto(savedDocument);
    }

    public DocumentResponseDto getDocument(UUID id) {
        Optional<Document> document = documentRepository.findById(id);
        return document.map(this::toResponseDto).orElse(null);
    }

    public DocumentResponseDto updateDocument(UUID id, DocumentRequestDto documentRequestDto) {
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            document.setName(documentRequestDto.getName());
            document.setUrl(documentRequestDto.getUrl());
            document.setDocumentType(documentRequestDto.getDocumentType());

            Optional<Profile> profileOptional = profileRepository.findById(documentRequestDto.getProfileId());
            if (profileOptional.isPresent()) {
                document.setProfile(profileOptional.get());
            } else {
                throw new IllegalArgumentException("Profile not found");
            }

            Document updatedDocument = documentRepository.save(document);
            return toResponseDto(updatedDocument);
        }
        return null;
    }

    public void deleteDocument(UUID id) {
        documentRepository.deleteById(id);
    }

    public Document findById(UUID id) {
        Optional<Document> documentOptional = documentRepository.findById(id);
        return documentOptional.orElse(null);
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
