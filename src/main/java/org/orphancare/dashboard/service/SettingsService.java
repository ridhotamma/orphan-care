package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.SettingsDto;
import org.orphancare.dashboard.entity.BankAccount;
import org.orphancare.dashboard.entity.Settings;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.SettingsMapper;
import org.orphancare.dashboard.repository.SettingsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingsService {

    private final SettingsRepository settingsRepository;
    private final SettingsMapper settingsMapper;

    public SettingsDto getSettings() {
        Settings settings = settingsRepository.findFirstByOrderByCreatedAtAsc()
                .orElseGet(() -> {
                    Settings defaultSettings = new Settings();
                    defaultSettings.setEnableChildSubmission(false);
                    defaultSettings.setEnableDonationPortal(false);
                    defaultSettings.setOrgPhoneNumber("");
                    defaultSettings.setBankAccounts(new ArrayList<>());
                    return settingsRepository.save(defaultSettings);
                });

        return settingsMapper.toDto(settings);
    }

    public SettingsDto updateSettings(SettingsDto settingsDto) {
        Settings settings = settingsRepository.findFirstByOrderByCreatedAtAsc()
                .orElseThrow(() -> new ResourceNotFoundException("Settings not found"));

        settings.setEnableChildSubmission(settingsDto.getEnableChildSubmission());
        settings.setEnableDonationPortal(settingsDto.getEnableDonationPortal());
        settings.setOrgPhoneNumber(settingsDto.getOrgPhoneNumber());

        List<BankAccount> bankAccounts = settingsDto.getBankAccounts().stream()
                .map(settingsMapper::toBankAccountEntity)
                .collect(Collectors.toList());
        settings.setBankAccounts(bankAccounts);

        return settingsMapper.toDto(settingsRepository.save(settings));
    }
}
