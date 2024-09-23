package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.orphancare.dashboard.dto.InventoryDto;
import org.orphancare.dashboard.entity.Inventory;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    @Mapping(source = "inventoryType.id", target = "inventoryTypeId")
    @Mapping(source = "unit.id", target = "unitId")
    InventoryDto toDto(Inventory inventory);

    @Mapping(source = "inventoryTypeId", target = "inventoryType.id")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "inventoryType", target = "inventoryType")
    @Mapping(source = "unit", target = "unit")
    Inventory toEntity(InventoryDto inventoryDto);
}