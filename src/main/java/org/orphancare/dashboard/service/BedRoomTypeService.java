package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.BedRoomTypeDto;
import org.orphancare.dashboard.entity.BedRoomType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.BedRoomTypeMapper;
import org.orphancare.dashboard.repository.BedRoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BedRoomTypeService {

    private final BedRoomTypeRepository bedRoomTypeRepository;
    private final BedRoomTypeMapper bedRoomTypeMapper;

    public List<BedRoomTypeDto> getAllBedRoomTypes() {
        return bedRoomTypeRepository.findAll()
                .stream()
                .map(bedRoomTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public BedRoomTypeDto getBedRoomTypeById(UUID id) {
        BedRoomType bedRoomType = bedRoomTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bed room type not found"));

        return bedRoomTypeMapper.toDto(bedRoomType);
    }

    public BedRoomTypeDto createBedRoomType(BedRoomTypeDto bedRoomTypeDto) {
        BedRoomType bedRoomType = bedRoomTypeMapper.toEntity(bedRoomTypeDto);
        return bedRoomTypeMapper.toDto(bedRoomTypeRepository.save(bedRoomType));
    }

    public BedRoomTypeDto updateBedRoomType(UUID id, BedRoomTypeDto bedRoomTypeDto) {
        BedRoomType existingBedRoomType = bedRoomTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bed room type not found"));

        existingBedRoomType.setType(bedRoomTypeDto.getType());
        existingBedRoomType.setName(bedRoomTypeDto.getName());

        return bedRoomTypeMapper.toDto(bedRoomTypeRepository.save(existingBedRoomType));
    }

    public void deleteBedRoomTypeById(UUID id) {
        if (!bedRoomTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bed room type not found with id: " + id);
        }
        bedRoomTypeRepository.deleteById(id);
    }
}
