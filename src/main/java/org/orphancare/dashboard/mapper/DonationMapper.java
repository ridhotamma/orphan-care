package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.orphancare.dashboard.dto.DonationDto;
import org.orphancare.dashboard.entity.Donation;

@Mapper(componentModel = "spring", uses = {DonationTypeMapper.class})
public interface DonationMapper {
    @Mapping(target = "donationTypeId", source = "donationType.id")
    DonationDto toDto(Donation donation);

    @Mapping(target = "donationType", ignore = true)
    Donation toEntity(DonationDto donationDto);
}