package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class DocumentDto {

    private UUID id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String url;

    @NotNull
    private UUID documentTypeId;

    private LocalDateTime createdAt;

    private DocumentTypeDto documentType;

    @Data
    public static class Response {
        private UUID id;
        private String name;
        private String url;
        private DocumentTypeDto documentType;
        private UserDto.DocumentOwnerDto owner;
        private LocalDateTime createdAt;
    }

    @Data
    @AllArgsConstructor
    public static class GroupByDateRange {
        private String type;
        private String range;
        private List<DocumentDto.Response> documents;
    }
}

