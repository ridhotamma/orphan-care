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

    private AddressDto address;

    @Data
    public static class Response {
        private UUID id;

        @NotNull
        private String fullName;

        @NotNull
        private String phoneNumber;

        private AddressDto address;
    }
}
