package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.orphancare.dashboard.dto.DonationTypeDto;
import org.orphancare.dashboard.entity.DonationType;

@Mapper(componentModel = "spring")
public interface DonationTypeMapper {
    DonationTypeDto toDto(DonationType donationType);
    DonationType toEntity(DonationTypeDto donationTypeDto);
}