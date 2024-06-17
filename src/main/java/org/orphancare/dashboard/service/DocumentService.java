package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentDto;
import org.orphancare.dashboard.entity.Document;
import org.orphancare.dashboard.entity.DocumentType;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.repository.DocumentRepository;
import org.orphancare.dashboard.repository.DocumentTypeRepository;
import org.orphancare.dashboard.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final UserRepository userRepository;

    public DocumentDto createDocument(UUID userId, DocumentDto documentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        DocumentType documentType = documentTypeRepository.findById(documentDto.getDocumentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with id " + documentDto.getDocumentTypeId()));

        Document document = new Document();
        document.setName(documentDto.getName());
        document.setUrl(documentDto.getUrl());
        document.setOwner(user);
        document.setDocumentType(documentType);

        Document savedDocument = documentRepository.save(document);
        return convertToDto(savedDocument);
    }

    public DocumentDto updateDocument(UUID documentId, DocumentDto documentDto) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id " + documentId));

        DocumentType documentType = documentTypeRepository.findById(documentDto.getDocumentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with id " + documentDto.getDocumentTypeId()));

        document.setName(documentDto.getName());
        document.setUrl(documentDto.getUrl());
        document.setDocumentType(documentType);

        Document updatedDocument = documentRepository.save(document);
        return convertToDto(updatedDocument);
    }

    public void deleteDocument(UUID documentId) {
        if (!documentRepository.existsById(documentId)) {
            throw new ResourceNotFoundException("Document not found with id " + documentId);
        }
        documentRepository.deleteById(documentId);
    }

    public DocumentDto.Response getDocumentById(UUID documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id " + documentId));
        return convertToDtoResponse(document);
    }

    public List<DocumentDto.Response> getAllDocumentsByUserId(UUID userId) {
        List<Document> documents = documentRepository.findByOwnerId(userId);
        return documents.stream().map(this::convertToDtoResponseDto).collect(Collectors.toList());
    }

    private DocumentDto convertToDto(Document document) {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(document.getId());
        documentDto.setName(document.getName());
        documentDto.setUrl(document.getUrl());
        documentDto.setDocumentTypeId(document.getDocumentType().getId());
        return documentDto;
    }

    private DocumentDto.Response convertToDtoResponseDto(Document document) {
        DocumentDto.Response documentDto = new DocumentDto.Response();
        documentDto.setId(document.getId());
        documentDto.setName(document.getName());
        documentDto.setUrl(document.getUrl());
        documentDto.setDocumentType(document.getDocumentType());
        return documentDto;
    }
}
