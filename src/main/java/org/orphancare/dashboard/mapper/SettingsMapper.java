package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.orphancare.dashboard.dto.SettingsDto;
import org.orphancare.dashboard.entity.Settings;

@Mapper(componentModel = "spring")
public interface SettingsMapper {
    SettingsDto toDto(Settings settings);
    Settings toEntity(SettingsDto settingsDto);
}
