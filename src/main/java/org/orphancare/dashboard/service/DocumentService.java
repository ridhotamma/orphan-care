package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentDto;
import org.orphancare.dashboard.entity.Document;
import org.orphancare.dashboard.entity.DocumentType;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.DocumentMapper;
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
    private final DocumentMapper documentMapper;

    public DocumentDto createDocument(UUID userId, DocumentDto documentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        DocumentType documentType = documentTypeRepository.findById(documentDto.getDocumentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with id " + documentDto.getDocumentTypeId()));

        Document document = documentMapper.toEntity(documentDto);
        document.setOwner(user);
        document.setDocumentType(documentType);

        Document savedDocument = documentRepository.save(document);
        return documentMapper.toDto(savedDocument);
    }

    public DocumentDto updateDocument(UUID documentId, DocumentDto documentDto) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id " + documentId));

        DocumentType documentType = documentTypeRepository.findById(documentDto.getDocumentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with id " + documentDto.getDocumentTypeId()));

        documentMapper.updateDocumentFromDto(documentDto, document);
        document.setDocumentType(documentType);

        Document updatedDocument = documentRepository.save(document);
        return documentMapper.toDto(updatedDocument);
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
        return documentMapper.toResponseDto(document);
    }

    public List<DocumentDto.Response> getAllDocumentsByUserId(UUID userId) {
        List<Document> documents = documentRepository.findByOwnerId(userId);
        return documents.stream().map(documentMapper::toResponseDto).collect(Collectors.toList());
    }
}
