package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.BedRoomDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.entity.BedRoom;
import org.orphancare.dashboard.entity.BedRoomType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.BedRoomMapper;
import org.orphancare.dashboard.repository.BedRoomRepository;
import org.orphancare.dashboard.repository.BedRoomTypeRepository;
import org.orphancare.dashboard.specification.BedRoomSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BedRoomService {

    private final BedRoomRepository bedRoomRepository;
    private final BedRoomTypeRepository bedRoomTypeRepository;
    private final BedRoomMapper bedRoomMapper;

    public PaginatedResponse<List<BedRoomDto>> getAllBedrooms(String name, UUID bedRoomTypeId, String sortBy, String sortOrder, int page, int perPage) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, perPage, sort);

        Specification<BedRoom> spec = Specification.where(BedRoomSpecification.nameContains(name))
                .and(BedRoomSpecification.bedRoomTypeIdEquals(bedRoomTypeId));

        Page<BedRoom> bedRoomsPage = bedRoomRepository.findAll(spec, pageable);

        List<BedRoomDto> bedRoomDtos = bedRoomsPage.getContent().stream()
                .map(bedRoomMapper::toDto)
                .collect(Collectors.toList());

        PaginatedResponse.Meta meta = new PaginatedResponse.Meta(
                bedRoomsPage.getNumber(),
                bedRoomsPage.getSize(),
                bedRoomsPage.getTotalElements(),
                bedRoomsPage.getTotalPages()
        );

        return new PaginatedResponse<>(bedRoomDtos, meta);
    }

    public List<BedRoomDto.DropdownDto> getBedRoomDropdownList() {
        return bedRoomRepository.findAll()
                .stream()
                .map(bedRoomMapper::toDropdownDto)
                .toList();
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
