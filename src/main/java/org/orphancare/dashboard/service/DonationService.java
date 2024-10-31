package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.DonationDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.entity.Donation;
import org.orphancare.dashboard.entity.DonationType;
import org.orphancare.dashboard.entity.Unit;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.DonationMapper;
import org.orphancare.dashboard.repository.DonationRepository;
import org.orphancare.dashboard.repository.DonationTypeRepository;
import org.orphancare.dashboard.repository.UnitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonationTypeRepository donationTypeRepository;
    private final UnitRepository unitRepository;
    private final DonationMapper donationMapper;

    public PaginatedResponse<List<DonationDto>> getAllDonations(
            String name,
            LocalDate startDate,
            LocalDate endDate,
            int page,
            int perPage) {

        Pageable pageable = PageRequest.of(page, perPage);
        Page<Donation> donationsPage;

        if (startDate != null && endDate != null) {
            donationsPage = (name == null || name.isEmpty()) ?
                    donationRepository.findByReceivedDateBetween(startDate, endDate, pageable) :
                    donationRepository.findByReceivedDateBetweenAndNameContainingIgnoreCase(
                            startDate, endDate, name, pageable);
        } else {
            donationsPage = (name == null || name.isEmpty()) ?
                    donationRepository.findAll(pageable) :
                    donationRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        List<DonationDto> donationDtos = donationsPage.getContent()
                .stream()
                .map(donationMapper::toDto)
                .collect(Collectors.toList());

        PaginatedResponse.Meta meta = new PaginatedResponse.Meta(
                donationsPage.getNumber(),
                donationsPage.getSize(),
                donationsPage.getTotalElements(),
                donationsPage.getTotalPages()
        );

        return new PaginatedResponse<>(donationDtos, meta);
    }

    public DonationDto getDonationById(UUID id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found"));

        return donationMapper.toDto(donation);
    }

    public DonationDto createDonation(DonationDto donationDto) {
        Donation donation = donationMapper.toEntity(donationDto);
        DonationType donationType = donationTypeRepository.findById(donationDto.getDonationTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Donation type not found"));
        Unit unit = unitRepository.findById(donationDto.getUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));
        donation.setDonationType(donationType);
        donation.setUnit(unit);
        return donationMapper.toDto(donationRepository.save(donation));
    }

    public DonationDto updateDonation(UUID id, DonationDto donationDto) {
        Donation existingDonation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found"));

        existingDonation.setName(donationDto.getName());
        existingDonation.setAmount(donationDto.getAmount());
        existingDonation.setReceivedDate(donationDto.getReceivedDate());
        existingDonation.setReceiver(donationDto.getReceiver());
        existingDonation.setDonatorName(donationDto.getDonatorName());

        DonationType donationType = donationTypeRepository.findById(donationDto.getDonationTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Donation type not found"));
        existingDonation.setDonationType(donationType);

        Unit unit = unitRepository.findById(donationDto.getUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));
        existingDonation.setUnit(unit);

        return donationMapper.toDto(donationRepository.save(existingDonation));
    }

    public void deleteDonationById(UUID id) {
        if (!donationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Donation not found with id: " + id);
        }
        donationRepository.deleteById(id);
    }
}
