package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.InventoryDto;
import org.orphancare.dashboard.entity.Inventory;
import org.orphancare.dashboard.entity.InventoryType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.InventoryMapper;
import org.orphancare.dashboard.repository.InventoryRepository;
import org.orphancare.dashboard.repository.InventoryTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryTypeRepository inventoryTypeRepository;
    private final InventoryMapper inventoryMapper;

    public Page<InventoryDto> getAllInventories(String name, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Inventory> inventoriesPage = (name == null || name.isEmpty()) ?
                inventoryRepository.findAll(pageable) :
                inventoryRepository.findByNameContainingIgnoreCase(name, pageable);
        return inventoriesPage.map(inventoryMapper::toDto);
    }

    public InventoryDto getInventoryById(UUID inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        return inventoryMapper.toDto(inventory);
    }

    public InventoryDto createInventory(InventoryDto inventoryDto) {
        InventoryType inventoryType = inventoryTypeRepository.findById(inventoryDto.getInventoryTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory type not found"));

        Inventory inventory = inventoryMapper.toEntity(inventoryDto);
        inventory.setInventoryType(inventoryType);
        inventory = inventoryRepository.save(inventory);

        return inventoryMapper.toDto(inventory);
    }

    public InventoryDto updateInventory(UUID id, InventoryDto inventoryDto) {
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        InventoryType inventoryType = inventoryTypeRepository.findById(inventoryDto.getInventoryTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory type not found"));

        existingInventory.setName(inventoryDto.getName());
        existingInventory.setQuantity(inventoryDto.getQuantity());
        existingInventory.setInventoryType(inventoryType);

        existingInventory = inventoryRepository.save(existingInventory);

        return inventoryMapper.toDto(existingInventory);
    }

    public void deleteInventory(UUID id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory not found with id: " + id);
        }
        inventoryRepository.deleteById(id);
    }
}
