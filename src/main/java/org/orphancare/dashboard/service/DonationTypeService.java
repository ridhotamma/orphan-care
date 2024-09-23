package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DonationTypeDto;
import org.orphancare.dashboard.entity.DonationType;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.DonationTypeMapper;
import org.orphancare.dashboard.repository.DonationTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationTypeService {

    private final DonationTypeRepository donationTypeRepository;
    private final DonationTypeMapper donationTypeMapper;

    public List<DonationTypeDto> getAllDonationTypes() {
        return donationTypeRepository.findAll()
                .stream()
                .map(donationTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public DonationTypeDto getDonationTypeById(UUID id) {
        DonationType donationType = donationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation type not found"));

        return donationTypeMapper.toDto(donationType);
    }

    public DonationTypeDto createDonationType(DonationTypeDto donationTypeDto) {
        DonationType donationType = donationTypeMapper.toEntity(donationTypeDto);
        return donationTypeMapper.toDto(donationTypeRepository.save(donationType));
    }

    public DonationTypeDto updateDonationType(UUID id, DonationTypeDto donationTypeDto) {
        DonationType existingDonationType = donationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation type not found"));

        existingDonationType.setType(donationTypeDto.getType());
        existingDonationType.setName(donationTypeDto.getName());

        return donationTypeMapper.toDto(donationTypeRepository.save(existingDonationType));
    }

    public void deleteDonationTypeById(UUID id) {
        if (!donationTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Donation type not found with id: " + id);
        }
        donationTypeRepository.deleteById(id);
    }
}
