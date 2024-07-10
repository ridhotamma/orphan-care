package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.orphancare.dashboard.dto.DocumentDto;
import org.orphancare.dashboard.entity.Document;

@Mapper(componentModel = "spring", uses = { DocumentTypeMapper.class })
public interface DocumentMapper {
    @Mapping(source = "documentType.id", target = "documentTypeId")
    DocumentDto toDto(Document document);

    @Mapping(source = "documentTypeId", target = "documentType.id")
    @Mapping(source = "documentType", target = "documentType")
    Document toEntity(DocumentDto documentDto);

    @Mapping(source = "documentType", target = "documentType")
    @Mapping(source = "createdAt", target = "createdAt")
    DocumentDto.Response toResponseDto(Document document);

    void updateDocumentFromDto(DocumentDto documentDto, @MappingTarget Document document);
}
