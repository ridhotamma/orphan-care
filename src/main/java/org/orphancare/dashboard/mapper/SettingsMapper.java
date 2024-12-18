package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.orphancare.dashboard.dto.SettingsDto;
import org.orphancare.dashboard.entity.BankAccount;
import org.orphancare.dashboard.entity.Settings;

@Mapper(componentModel = "spring")
public interface SettingsMapper {
    SettingsDto toDto(Settings settings);
    Settings toEntity(SettingsDto settingsDto);

    SettingsDto.BankAccountDto toBankAccountDto(BankAccount bankAccount);
    BankAccount toBankAccountEntity(SettingsDto.BankAccountDto bankAccountDto);
}
