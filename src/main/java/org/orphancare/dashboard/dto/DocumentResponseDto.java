package org.orphancare.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orphancare.dashboard.entity.DocumentType;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDto {

    private UUID id;
    private String name;
    private String url;
    private UUID profileId;
    private DocumentType documentType;
}
