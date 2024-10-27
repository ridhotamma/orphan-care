package org.orphancare.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.AddressDto.ProvinceDto;
import org.orphancare.dashboard.dto.AddressDto.RegencyDto;
import org.orphancare.dashboard.dto.AddressDto.DistrictDto;
import org.orphancare.dashboard.dto.AddressDto.VillageDto;
import org.orphancare.dashboard.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/provinces")
    public ResponseEntity<List<ProvinceDto>> getAllProvinces() {
        List<ProvinceDto> provinces = addressService.getAllProvinces();
        return ResponseEntity.ok(provinces);
    }

    @GetMapping("/provinces/{provinceId}/regencies")
    public ResponseEntity<List<RegencyDto>> getRegenciesByProvinceId(
            @PathVariable String provinceId
    ) {
        List<RegencyDto> regencies = addressService.getRegenciesByProvinceId(provinceId);
        return ResponseEntity.ok(regencies);
    }

    @GetMapping("/regencies/{regencyId}/districts")
    public ResponseEntity<List<DistrictDto>> getDistrictsByRegencyId(
            @PathVariable String regencyId
    ) {
        List<DistrictDto> districts = addressService.getDistrictsByRegencyId(regencyId);
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/districts/{districtId}/villages")
    public ResponseEntity<List<VillageDto>> getVillagesByDistrictId(
            @PathVariable String districtId
    ) {
        List<VillageDto> villages = addressService.getVillagesByDistrictId(districtId);
        return ResponseEntity.ok(villages);
    }
}
