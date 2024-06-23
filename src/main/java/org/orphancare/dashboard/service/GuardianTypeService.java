package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.GuardianDto;
import org.orphancare.dashboard.dto.GuardianTypeDto;
import org.orphancare.dashboard.entity.GuardianType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.GuardianTypeMapper;
import org.orphancare.dashboard.repository.GuardianTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GuardianTypeService {

    private final GuardianTypeRepository guardianTypeRepository;
    private final GuardianTypeMapper guardianTypeMapper;

    public GuardianTypeDto createGuardianType(GuardianTypeDto guardianTypeDto) {
        GuardianType guardianType = guardianTypeMapper.toEntity(guardianTypeDto);
        return guardianTypeMapper.toDto(guardianTypeRepository.save(guardianType));
    }

    public List<GuardianTypeDto> getAllGuardianTypes() {
        return guardianTypeRepository.findAll()
                .stream()
                .map(guardianTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public GuardianTypeDto getGuardianTypeById(UUID guardianTypeId) {
        GuardianType guardianType = guardianTypeRepository.findById(guardianTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Guardian type not found with id " + guardianTypeId));

        return guardianTypeMapper.toDto(guardianType);
    }

    public GuardianTypeDto updateGuardianType(UUID guardianTypeId, GuardianTypeDto guardianTypeDto) {
        GuardianType existingGuardianType = guardianTypeRepository.findById(guardianTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Guardian type not found with id " + guardianTypeId));

        existingGuardianType.setName(guardianTypeDto.getName());
        existingGuardianType.setType(guardianTypeDto.getType());

        GuardianType guardianType = guardianTypeRepository.save(existingGuardianType);

        return guardianTypeMapper.toDto(guardianType);
    }

    public void deleteGuardianTypeById(UUID guardianTypeId) {
        if (!guardianTypeRepository.existsById(guardianTypeId)) {
            throw new ResourceNotFoundException("Guardian not found with id " + guardianTypeId);
        }

        guardianTypeRepository.deleteById(guardianTypeId);
    }

}
