package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.BedRoomDto;
import org.orphancare.dashboard.entity.BedRoom;
import org.orphancare.dashboard.entity.BedRoomType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.BedRoomMapper;
import org.orphancare.dashboard.repository.BedRoomRepository;
import org.orphancare.dashboard.repository.BedRoomTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BedRoomService {

    private final BedRoomRepository bedRoomRepository;
    private final BedRoomTypeRepository bedRoomTypeRepository;
    private final BedRoomMapper bedRoomMapper;

    public Page<BedRoomDto> getAllBedrooms(String name, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<BedRoom> bedRoomsPage = (name == null || name.isEmpty()) ?
                bedRoomRepository.findAll(pageable) :
                bedRoomRepository.findByNameContainingIgnoreCase(name, pageable);
        return bedRoomsPage.map(bedRoomMapper::toDto);
    }

    public BedRoomDto getBedRoomById(UUID bedRoomId) {
        BedRoom bedRoom = bedRoomRepository.findById(bedRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Bed room not found"));

        return bedRoomMapper.toDto(bedRoom);
    }

    public BedRoomDto createBedRoom(BedRoomDto bedRoomDto) {
        BedRoomType bedRoomType = bedRoomTypeRepository.findById(bedRoomDto.getBedRoomTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Bed room type not found"));
        BedRoom bedRoom = bedRoomMapper.toEntity(bedRoomDto);
        bedRoom.setBedRoomType(bedRoomType);
        bedRoom = bedRoomRepository.save(bedRoom);
        return bedRoomMapper.toDto(bedRoom);
    }

    public BedRoomDto updateBedRoom(UUID id, BedRoomDto bedRoomDto) {
        BedRoom existingBedRoom = bedRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed room not found"));

        BedRoomType bedRoomType = bedRoomTypeRepository.findById(bedRoomDto.getBedRoomTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Bed room type not found"));

        existingBedRoom.setName(bedRoomDto.getName());
        existingBedRoom.setBedRoomType(bedRoomType);
        existingBedRoom = bedRoomRepository.save(existingBedRoom);
        return bedRoomMapper.toDto(existingBedRoom);
    }

    public void deleteBedRoom(UUID id) {
        if (!bedRoomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bed room not found with id: " + id);
        }
        bedRoomRepository.deleteById(id);
    }
}
