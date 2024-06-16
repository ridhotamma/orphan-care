package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.AddressRequestDto;
import org.orphancare.dashboard.dto.AddressResponseDto;
import org.orphancare.dashboard.entity.Address;
import org.orphancare.dashboard.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressResponseDto createAddress(AddressRequestDto addressRequestDto) {
        Address address = new Address();
        address.setStreet(addressRequestDto.getStreet());
        address.setUrbanVillage(addressRequestDto.getUrbanVillage());
        address.setSubdistrict(addressRequestDto.getSubdistrict());
        address.setCity(addressRequestDto.getCity());
        address.setProvince(addressRequestDto.getProvince());
        address.setPostalCode(addressRequestDto.getPostalCode());

        Address savedAddress = addressRepository.save(address);
        return toResponseDto(savedAddress);
    }

    public AddressResponseDto getAddress(UUID id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.map(this::toResponseDto).orElse(null);
    }

    public AddressResponseDto updateAddress(UUID id, AddressRequestDto addressRequestDto) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            address.setStreet(addressRequestDto.getStreet());
            address.setUrbanVillage(addressRequestDto.getUrbanVillage());
            address.setSubdistrict(addressRequestDto.getSubdistrict());
            address.setCity(addressRequestDto.getCity());
            address.setProvince(addressRequestDto.getProvince());
            address.setPostalCode(addressRequestDto.getPostalCode());

            Address updatedAddress = addressRepository.save(address);
            return toResponseDto(updatedAddress);
        }
        return null;
    }

    public Address findById(UUID id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        return addressOptional.orElse(null);
    }

    private AddressResponseDto toResponseDto(Address address) {
        return new AddressResponseDto(
                address.getId(),
                address.getStreet(),
                address.getUrbanVillage(),
                address.getSubdistrict(),
                address.getCity(),
                address.getProvince(),
                address.getPostalCode()
        );
    }
}
