package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentDto;
import org.orphancare.dashboard.dto.DocumentTypeDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.entity.Document;
import org.orphancare.dashboard.entity.DocumentType;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.DocumentMapper;
import org.orphancare.dashboard.mapper.DocumentTypeMapper;
import org.orphancare.dashboard.repository.DocumentRepository;
import org.orphancare.dashboard.repository.DocumentTypeRepository;
import org.orphancare.dashboard.repository.UserRepository;
import org.orphancare.dashboard.specification.DocumentSpecification;
import org.orphancare.dashboard.util.RequestUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private final DocumentTypeMapper documentTypeMapper;
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

    public PaginatedResponse<List<DocumentDto.Response>> getAllDocumentsByUserId(UUID userId, String name, UUID documentTypeId, String sortBy, String sortOrder, int page, int perPage) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, perPage, sort);

        Specification<Document> spec = Specification.where(DocumentSpecification.ownerIdEquals(userId))
                .and(DocumentSpecification.nameContains(name))
                .and(DocumentSpecification.documentTypeIdEquals(documentTypeId));

        Page<Document> documentsPage = documentRepository.findAll(spec, pageable);

        List<DocumentDto.Response> documentDtos = documentsPage.getContent().stream()
                .map(documentMapper::toResponseDto)
                .collect(Collectors.toList());

        PaginatedResponse.Meta meta = new PaginatedResponse.Meta(
                documentsPage.getNumber(),
                documentsPage.getSize(),
                documentsPage.getTotalElements(),
                documentsPage.getTotalPages()
        );

        return new PaginatedResponse<>(documentDtos, meta);
    }

    public PaginatedResponse<List<DocumentDto.Response>> getCurrentUserDocuments(String name, UUID documentTypeId, String sortBy, String sortOrder, int page, int perPage) {
        String currentUsername = requestUtil.getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not exists with username " + currentUsername));

        return getAllDocumentsByUserId(user.getId(), name, documentTypeId, sortBy, sortOrder, page, perPage);
    }

    public List<DocumentTypeDto> getMissingMandatoryDocuments(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        List<DocumentType> allMandatoryTypes = documentTypeRepository.findByIsMandatoryTrue();
        List<DocumentType> uploadedMandatoryTypes = documentRepository.findDistinctMandatoryDocumentTypesByOwner(user);

        return allMandatoryTypes.stream()
                .filter(mandatoryType -> !uploadedMandatoryTypes.contains(mandatoryType))
                .map(documentTypeMapper::toDto)
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

    public PaginatedResponse<List<DocumentDto.Response>> getAllDocuments(
            String name, UUID documentTypeId, UUID ownerId,
            String sortBy, String sortOrder, int page, int perPage) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, perPage, sort);

        Specification<Document> spec = Specification.where(DocumentSpecification.nameContains(name))
                .and(DocumentSpecification.documentTypeIdEquals(documentTypeId))
                .and(DocumentSpecification.ownerIdEquals(ownerId));

        Page<Document> documentsPage = documentRepository.findAll(spec, pageable);

        List<DocumentDto.Response> documentDtos = documentsPage.getContent().stream()
                .map(documentMapper::toResponseDto)
                .collect(Collectors.toList());

        PaginatedResponse.Meta meta = new PaginatedResponse.Meta(
                documentsPage.getNumber(),
                documentsPage.getSize(),
                documentsPage.getTotalElements(),
                documentsPage.getTotalPages()
        );

        return new PaginatedResponse<>(documentDtos, meta);
    }
}
