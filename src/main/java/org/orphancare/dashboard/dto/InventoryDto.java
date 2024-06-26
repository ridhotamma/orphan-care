package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.orphancare.dashboard.entity.InventoryType;

import java.math.BigInteger;
import java.util.UUID;

@Data
public class InventoryDto {
    private UUID id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private BigInteger quantity;

    @NotBlank
    @NotNull
    private UUID inventoryTypeId;

    private InventoryType inventoryType;
}
