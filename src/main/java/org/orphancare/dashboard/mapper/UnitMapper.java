package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.orphancare.dashboard.dto.UnitDto;
import org.orphancare.dashboard.entity.Unit;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    UnitDto toDto(Unit unit);
    Unit toEntity(UnitDto unitDto);
}