package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedRoomDto {

    private UUID id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private UUID bedRoomTypeId;

    private BedRoomTypeDto bedRoomType;

    private List<ProfileDto.ShortResponse> profiles;

    @Data
    public static class DropdownDto {
        private UUID id;
        private String name;
    }
}
