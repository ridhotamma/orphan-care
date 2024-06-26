package org.orphancare.dashboard.mapper;

import org.orphancare.dashboard.dto.InventoryTypeDto;
import org.orphancare.dashboard.entity.InventoryType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper
public interface InventoryTypeMapper {
    InventoryTypeDto toDto(InventoryType inventoryType);

    @Mapping(target = "id", ignore = true)
    InventoryType toEntity(InventoryTypeDto inventoryTypeDto);

    void updateFromDto(InventoryTypeDto inventoryTypeDto, @MappingTarget InventoryType inventoryType);
}
