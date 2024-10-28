package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.SettingsDto;
import org.orphancare.dashboard.entity.Settings;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.SettingsMapper;
import org.orphancare.dashboard.repository.SettingsRepository;
import org.springframework.stereotype.Service;

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
                    return settingsRepository.save(defaultSettings);
                });

        return settingsMapper.toDto(settings);
    }

    public SettingsDto updateSettings(SettingsDto settingsDto) {
        Settings settings = settingsRepository.findFirstByOrderByCreatedAtAsc()
                .orElseThrow(() -> new ResourceNotFoundException("Settings not found"));

        settings.setEnableChildSubmission(settingsDto.getEnableChildSubmission());
        settings.setEnableDonationPortal(settingsDto.getEnableDonationPortal());
        settings.setBankAccountNumbers(settingsDto.getBankAccountNumbers());
        settings.setOrgPhoneNumber(settingsDto.getOrgPhoneNumber());

        return settingsMapper.toDto(settingsRepository.save(settings));
    }
}
