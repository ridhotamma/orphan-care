package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.orphancare.dashboard.validation.ValidGender;

import java.time.LocalDate;

@Data
public class ProfileDto {

    @NotBlank
    private String fullName;

    private String profilePicture;
    private LocalDate birthday;
    private LocalDate joinDate;
    private LocalDate leaveDate;
    private String bio;

    @NotNull
    @ValidGender
    private String gender;

    private AddressDto address;

    @Data
    public static class ShortResponse {
        private String fullName;
        private String profilePicture;
    }
}
