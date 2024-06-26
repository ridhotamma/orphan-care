package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.InventoryTypeDto;
import org.orphancare.dashboard.entity.InventoryType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.InventoryTypeMapper;
import org.orphancare.dashboard.repository.InventoryTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryTypeService {

    private final InventoryTypeRepository inventoryTypeRepository;
    private final InventoryTypeMapper inventoryTypeMapper;

    public List<InventoryTypeDto> getAllInventoryTypes() {
        return inventoryTypeRepository.findAll()
                .stream()
                .map(inventoryTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public InventoryTypeDto getInventoryTypeById(UUID id) {
        InventoryType inventoryType = inventoryTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory type not found"));

        return inventoryTypeMapper.toDto(inventoryType);
    }

    public InventoryTypeDto createInventoryType(InventoryTypeDto inventoryTypeDto) {
        InventoryType inventoryType = inventoryTypeMapper.toEntity(inventoryTypeDto);
        return inventoryTypeMapper.toDto(inventoryTypeRepository.save(inventoryType));
    }

    public InventoryTypeDto updateInventoryType(UUID id, InventoryTypeDto inventoryTypeDto) {
        InventoryType existingInventoryType = inventoryTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory type not found"));

        inventoryTypeMapper.updateFromDto(inventoryTypeDto, existingInventoryType);

        return inventoryTypeMapper.toDto(inventoryTypeRepository.save(existingInventoryType));
    }

    public void deleteInventoryTypeById(UUID id) {
        if (!inventoryTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory type not found with id: " + id);
        }
        inventoryTypeRepository.deleteById(id);
    }
}
