package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.AddressDto.ProvinceDto;
import org.orphancare.dashboard.dto.AddressDto.RegencyDto;
import org.orphancare.dashboard.dto.AddressDto.DistrictDto;
import org.orphancare.dashboard.dto.AddressDto.VillageDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://ridhotamma.github.io/api-wilayah-indonesia/api";

    public List<ProvinceDto> getAllProvinces() {
        String url = BASE_URL + "/provinces.json";
        ResponseEntity<List<ProvinceDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public List<RegencyDto> getRegenciesByProvinceId(String provinceId) {
        String url = BASE_URL + "/regencies/" + provinceId + ".json";
        ResponseEntity<List<RegencyDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public List<DistrictDto> getDistrictsByRegencyId(String regencyId) {
        String url = BASE_URL + "/districts/" + regencyId + ".json";
        ResponseEntity<List<DistrictDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public List<VillageDto> getVillagesByDistrictId(String districtId) {
        String url = BASE_URL + "/villages/" + districtId + ".json";
        ResponseEntity<List<VillageDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }
}
