package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Data
public class InventoryDto {
    private UUID id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private BigInteger quantity;

    @NotNull
    private UUID inventoryTypeId;

    private InventoryTypeDto inventoryType;
}
