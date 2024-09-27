package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.GuardianDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.entity.Guardian;
import org.orphancare.dashboard.entity.GuardianType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.GuardianMapper;
import org.orphancare.dashboard.repository.GuardianRepository;
import org.orphancare.dashboard.repository.GuardianTypeRepository;
import org.orphancare.dashboard.specification.GuardianSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GuardianService {

    private final GuardianRepository guardianRepository;
    private final GuardianTypeRepository guardianTypeRepository;
    private final GuardianMapper guardianMapper;

    public GuardianDto.Response createGuardian(GuardianDto guardianDto) {
        GuardianType guardianType = guardianTypeRepository.findById(guardianDto.getGuardianTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Guardian type not found with id " + guardianDto.getGuardianTypeId()));

        Guardian guardian = guardianMapper.toEntity(guardianDto);
        guardian.setGuardianType(guardianType);

        Guardian savedGuardian = guardianRepository.save(guardian);
        return guardianMapper.toResponseDto(savedGuardian);
    }

    public GuardianDto.Response updateGuardian(UUID guardianId, GuardianDto guardianDto) {
        Guardian guardian = guardianRepository.findById(guardianId)
                .orElseThrow(() -> new ResourceNotFoundException("Guardian not found with id " + guardianId));

        GuardianType guardianType = guardianTypeRepository.findById(guardianDto.getGuardianTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Guardian type not found with id " + guardianDto.getGuardianTypeId()));

        guardianMapper.updateGuardianFromDto(guardianDto, guardian);
        guardian.setGuardianType(guardianType);

        Guardian updatedGuardian = guardianRepository.save(guardian);
        return guardianMapper.toResponseDto(updatedGuardian);
    }

    public void deleteGuardian(UUID guardianId) {
        if (!guardianRepository.existsById(guardianId)) {
            throw new ResourceNotFoundException("Guardian not found with id " + guardianId);
        }
        guardianRepository.deleteById(guardianId);
    }

    public GuardianDto.Response getGuardianById(UUID guardianId) {
        Guardian guardian = guardianRepository.findById(guardianId)
                .orElseThrow(() -> new ResourceNotFoundException("Guardian not found with id " + guardianId));
        return guardianMapper.toResponseDto(guardian);
    }

    public PaginatedResponse<List<GuardianDto.Response>> getAllGuardians(String fullName, String email, UUID guardianTypeId, String sortBy, String sortOrder, int page, int perPage) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, perPage, Sort.by(direction, sortBy));

        Specification<Guardian> spec = GuardianSpecification.searchGuardians(fullName, email, guardianTypeId);
        Page<Guardian> guardianPage = guardianRepository.findAll(spec, pageable);

        List<GuardianDto.Response> guardianDtos = guardianPage.getContent().stream()
                .map(guardianMapper::toResponseDto)
                .collect(Collectors.toList());

        PaginatedResponse.Meta meta = new PaginatedResponse.Meta(
                guardianPage.getNumber(),
                guardianPage.getSize(),
                guardianPage.getTotalElements(),
                guardianPage.getTotalPages()
        );

        return new PaginatedResponse<>(guardianDtos, meta);
    }

}
