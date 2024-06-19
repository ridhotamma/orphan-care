package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.orphancare.dashboard.dto.AddressDto;
import org.orphancare.dashboard.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto toDto(Address address);

    Address toEntity(AddressDto addressDto);
}
