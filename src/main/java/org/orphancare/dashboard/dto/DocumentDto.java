package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class DocumentDto {

    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String url;

    @NotNull
    private UUID documentTypeId;
}
