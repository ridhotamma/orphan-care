package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.orphancare.dashboard.dto.InventoryDto;
import org.orphancare.dashboard.entity.Inventory;

@Mapper
public interface InventoryMapper {
    @Mapping(source = "inventoryType.id", target = "inventoryTypeId")
    InventoryDto inventoryToInventoryDto(Inventory inventory);

    @Mapping(source = "inventoryTypeId", target = "inventoryType.id")
    Inventory inventoryDtoToInventory(InventoryDto inventoryDto);
}
