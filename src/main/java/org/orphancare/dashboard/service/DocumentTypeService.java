package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentTypeDto;
import org.orphancare.dashboard.entity.DocumentType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.repository.DocumentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeDto createDocumentType(DocumentTypeDto documentTypeDto) {
        DocumentType documentType = new DocumentType();
        documentType.setName(documentTypeDto.getName());
        documentType.setType(documentTypeDto.getType());

        DocumentType savedDocumentType = documentTypeRepository.save(documentType);
        return convertToDto(savedDocumentType);
    }

    public DocumentTypeDto updateDocumentType(UUID documentTypeId, DocumentTypeDto documentTypeDto) {
        DocumentType documentType = documentTypeRepository.findById(documentTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with id " + documentTypeId));

        documentType.setName(documentTypeDto.getName());
        documentType.setType(documentTypeDto.getType());

        DocumentType updatedDocumentType = documentTypeRepository.save(documentType);
        return convertToDto(updatedDocumentType);
    }

    public void deleteDocumentType(UUID documentTypeId) {
        if (!documentTypeRepository.existsById(documentTypeId)) {
            throw new ResourceNotFoundException("Document type not found with id " + documentTypeId);
        }
        documentTypeRepository.deleteById(documentTypeId);
    }

    public DocumentTypeDto getDocumentTypeById(UUID documentTypeId) {
        DocumentType documentType = documentTypeRepository.findById(documentTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with id " + documentTypeId));
        return convertToDto(documentType);
    }

    public List<DocumentTypeDto> getAllDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        return documentTypes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private DocumentTypeDto convertToDto(DocumentType documentType) {
        DocumentTypeDto documentTypeDto = new DocumentTypeDto();
        documentTypeDto.setId(documentType.getId());
        documentTypeDto.setName(documentType.getName());
        documentTypeDto.setType(documentType.getType());
        return documentTypeDto;
    }
}
