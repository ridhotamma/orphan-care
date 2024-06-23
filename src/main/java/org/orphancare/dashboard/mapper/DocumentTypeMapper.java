package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.orphancare.dashboard.dto.DocumentTypeDto;
import org.orphancare.dashboard.entity.DocumentType;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {
    DocumentTypeDto toDto(DocumentType documentType);
    DocumentType toEntity(DocumentTypeDto documentTypeDto);
    void updateDocumentTypeFromDto(DocumentTypeDto documentTypeDto, @MappingTarget DocumentType documentType);
}
