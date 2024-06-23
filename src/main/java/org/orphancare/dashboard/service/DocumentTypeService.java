package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DocumentTypeDto;
import org.orphancare.dashboard.entity.DocumentType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.DocumentTypeMapper;
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
    private final DocumentTypeMapper documentTypeMapper;

    public DocumentTypeDto createDocumentType(DocumentTypeDto documentTypeDto) {
        DocumentType documentType = documentTypeMapper.toEntity(documentTypeDto);
        DocumentType savedDocumentType = documentTypeRepository.save(documentType);
        return documentTypeMapper.toDto(savedDocumentType);
    }

    public DocumentTypeDto updateDocumentType(UUID documentTypeId, DocumentTypeDto documentTypeDto) {
        DocumentType documentType = documentTypeRepository.findById(documentTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Document type not found with id " + documentTypeId));

        documentTypeMapper.updateDocumentTypeFromDto(documentTypeDto, documentType);

        DocumentType updatedDocumentType = documentTypeRepository.save(documentType);
        return documentTypeMapper.toDto(updatedDocumentType);
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
        return documentTypeMapper.toDto(documentType);
    }

    public List<DocumentTypeDto> getAllDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        return documentTypes.stream().map(documentTypeMapper::toDto).collect(Collectors.toList());
    }
}
