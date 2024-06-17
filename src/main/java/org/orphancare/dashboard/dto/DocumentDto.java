package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.orphancare.dashboard.entity.DocumentType;

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

    @Data
    public static class Response {
        private UUID id;

        @NotBlank
        @NotNull
        private String name;

        @NotBlank
        @NotNull
        private String url;

        @NotNull
        private DocumentType documentType;
    }
}
