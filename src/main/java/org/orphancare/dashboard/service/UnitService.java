package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.UnitDto;
import org.orphancare.dashboard.entity.Unit;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.UnitMapper;
import org.orphancare.dashboard.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    public List<UnitDto> getAllUnits() {
        return unitRepository.findAll()
                .stream()
                .map(unitMapper::toDto)
                .collect(Collectors.toList());
    }

    public UnitDto getUnitById(UUID id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));

        return unitMapper.toDto(unit);
    }

    public UnitDto createUnit(UnitDto unitDto) {
        Unit unit = unitMapper.toEntity(unitDto);
        return unitMapper.toDto(unitRepository.save(unit));
    }

    public UnitDto updateUnit(UUID id, UnitDto unitDto) {
        Unit existingUnit = unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));

        existingUnit.setName(unitDto.getName());
        existingUnit.setType(unitDto.getType());

        return unitMapper.toDto(unitRepository.save(existingUnit));
    }

    public void deleteUnitById(UUID id) {
        if (!unitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Unit not found with id: " + id);
        }
        unitRepository.deleteById(id);
    }
}