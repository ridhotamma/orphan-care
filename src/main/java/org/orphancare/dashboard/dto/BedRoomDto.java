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

    @Data
    public static class ShortResponse {
        private UUID id;

        @NotBlank
        @NotNull
        private String name;

        @NotNull
        private UUID bedRoomTypeId;

        private BedRoomTypeDto bedRoomType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateBedRoomDto {
        @NotBlank(message = "Name is required")
        @NotNull(message = "Name cannot be null")
        private String name;

        @NotNull(message = "Bedroom type ID is required")
        private UUID bedRoomTypeId;

        private List<UUID> profiles;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateBedRoomDto {
        @NotBlank(message = "Name is required")
        @NotNull(message = "Name cannot be null")
        private String name;

        @NotNull(message = "Bedroom type ID is required")
        private UUID bedRoomTypeId;

        private List<UUID> profiles;
    }
}
