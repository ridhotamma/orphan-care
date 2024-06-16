package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orphancare.dashboard.entity.DocumentType;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequestDto {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String url;

    @NotNull
    private UUID profileId;

    @NotNull
    private DocumentType documentType;
}
