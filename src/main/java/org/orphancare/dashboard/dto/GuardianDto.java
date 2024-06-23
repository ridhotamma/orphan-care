package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class GuardianDto {

    private UUID id;

    @NotBlank
    @NotNull
    private String fullName;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull
    private UUID guardianTypeId;

    private AddressDto address;

    @Data
    public static class Response {
        private UUID id;
        private String fullName;
        private String phoneNumber;
        private AddressDto addressDto;
        private GuardianTypeDto guardianType;
    }
}
