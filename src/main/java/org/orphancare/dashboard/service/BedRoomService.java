package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.BedRoomDto;
import org.orphancare.dashboard.entity.BedRoom;
import org.orphancare.dashboard.entity.BedRoomType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.BedRoomMapper;
import org.orphancare.dashboard.repository.BedRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BedRoomService {

    private final BedRoomRepository bedRoomRepository;
    private final BedRoomMapper bedRoomMapper;

    public List<BedRoomDto> getAllBedrooms() {
        return bedRoomRepository.findAll()
                .stream()
                .map(bedRoomMapper::toDto)
                .collect(Collectors.toList());
    }

    public BedRoomDto getBedRoomById(UUID bedRoomId) {
        BedRoom bedRoom = bedRoomRepository.findById(bedRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Bed room not found"));

        return bedRoomMapper.toDto(bedRoom);
    }

    public BedRoomDto createBedRoom(BedRoomDto bedRoomDto) {
        BedRoom bedRoom = bedRoomMapper.toEntity(bedRoomDto);
        bedRoom.setBedRoomType(BedRoomType.valueOf(bedRoomDto.getBedRoomType().toUpperCase()));
        bedRoom = bedRoomRepository.save(bedRoom);
        return bedRoomMapper.toDto(bedRoom);
    }

    public BedRoomDto updateBedRoom(UUID id, BedRoomDto bedRoomDto) {
        BedRoom existingBedRoom = bedRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed room not found"));
        existingBedRoom.setName(bedRoomDto.getName());
        existingBedRoom.setBedRoomType(BedRoomType.valueOf(bedRoomDto.getBedRoomType().toUpperCase()));
        existingBedRoom = bedRoomRepository.save(existingBedRoom);
        return bedRoomMapper.toDto(existingBedRoom);
    }

    public void deleteBedRoom(UUID id) {
        bedRoomRepository.deleteById(id);
    }
}
