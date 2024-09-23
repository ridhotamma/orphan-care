package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.orphancare.dashboard.dto.DonationDto;
import org.orphancare.dashboard.entity.Donation;

@Mapper(componentModel = "spring", uses = {DonationTypeMapper.class, UnitMapper.class})
public interface DonationMapper {
    @Mapping(target = "donationTypeId", source = "donationType.id")
    @Mapping(target = "unitId", source = "unit.id")
    DonationDto toDto(Donation donation);

    @Mapping(source = "donationTypeId", target = "donationType.id")
    @Mapping(source = "donationType", target = "donationType")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "unit", target = "unit")
    Donation toEntity(DonationDto donationDto);
}