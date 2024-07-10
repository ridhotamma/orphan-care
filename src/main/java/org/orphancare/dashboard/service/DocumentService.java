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
import org.orphancare.dashboard.util.RequestUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final UserRepository userRepository;
    private final DocumentMapper documentMapper;
    private final RequestUtil requestUtil;

    public DocumentDto.Response createDocument(UUID userId, DocumentDto documentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        DocumentType documentType = documentTypeRepository.findById(documentDto.getDocumentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with id " + documentDto.getDocumentTypeId()));

        Document document = documentMapper.toEntity(documentDto);
        document.setOwner(user);
        document.setDocumentType(documentType);

        Document savedDocument = documentRepository.save(document);
        return documentMapper.toResponseDto(savedDocument);
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
        return documents.stream()
                .sorted(Comparator.comparing(Document::getCreatedAt).reversed())
                .map(documentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<DocumentDto.Response> getCurrentUserDocuments() {
        String currentUsername = requestUtil.getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not exists with username " + currentUsername));
        List<Document> documents = documentRepository.findByOwnerId(user.getId());
        return documents.stream()
                .sorted(Comparator.comparing(Document::getCreatedAt).reversed())
                .map(documentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<DocumentDto.GroupByDateRange> getDocumentsGroupedByDate(UUID userId) {
        List<Document> documents = documentRepository.findByOwnerId(userId);
        Map<String, List<DocumentDto.Response>> groupedDocuments = documents.stream()
                .map(documentMapper::toResponseDto)
                .collect(Collectors.groupingBy(this::getDateRange));

        List<DocumentDto.GroupByDateRange> response = new ArrayList<>();

        response.add(new DocumentDto.GroupByDateRange("TODAY", "Today", groupedDocuments.getOrDefault("TODAY", new ArrayList<>())));
        response.add(new DocumentDto.GroupByDateRange("LAST_7_DAYS", "Last 7 Days", groupedDocuments.getOrDefault("LAST_7_DAYS", new ArrayList<>())));
        response.add(new DocumentDto.GroupByDateRange("LAST_14_DAYS", "Last 14 Days", groupedDocuments.getOrDefault("LAST_14_DAYS", new ArrayList<>())));
        response.add(new DocumentDto.GroupByDateRange("LAST_30_DAYS", "Last 30 Days", groupedDocuments.getOrDefault("LAST_30_DAYS", new ArrayList<>())));
        response.add(new DocumentDto.GroupByDateRange("OLDER", "Older", groupedDocuments.getOrDefault("OLDER", new ArrayList<>())));

        return response;
    }


    private String getDateRange(DocumentDto.Response document) {
        LocalDate createdDate = document.getCreatedAt().toLocalDate();
        LocalDate today = LocalDate.now();

        if (createdDate.equals(today)) {
            return "TODAY";
        } else if (createdDate.isAfter(today.minusDays(7))) {
            return "LAST_7_DAYS";
        } else if (createdDate.isAfter(today.minusDays(14))) {
            return "LAST_14_DAYS";
        } else if (createdDate.isAfter(today.minusDays(30))) {
            return "LAST_30_DAYS";
        } else {
            return "OLDER";
        }
    }
}
