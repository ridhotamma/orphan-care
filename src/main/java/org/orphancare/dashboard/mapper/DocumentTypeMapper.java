package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.orphancare.dashboard.dto.DocumentTypeDto;
import org.orphancare.dashboard.entity.DocumentType;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {
    @Mapping(source = "mandatory", target = "mandatory")
    DocumentTypeDto toDto(DocumentType documentType);

    @Mapping(source = "mandatory", target = "mandatory")
    DocumentType toEntity(DocumentTypeDto documentTypeDto);

    @Mapping(source = "mandatory", target = "mandatory")
    void updateDocumentTypeFromDto(DocumentTypeDto documentTypeDto, @MappingTarget DocumentType documentType);
}
