package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class InventoryTypeDto {
    private UUID id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String type;
}
