package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.orphancare.dashboard.validation.ValidGender;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ProfileDto {

    @NotBlank
    private String fullName;

    private String profilePicture;
    private LocalDate birthday;
    private LocalDate joinDate;
    private LocalDate leaveDate;
    private String bio;
    private AddressDto address;
    private UUID bedRoomId;
    private BedRoomDto bedRoom;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull
    @ValidGender
    private String gender;

    @Data
    public static class ShortResponse {
        private String fullName;
        private String profilePicture;
        private String phoneNumber;
    }
}
