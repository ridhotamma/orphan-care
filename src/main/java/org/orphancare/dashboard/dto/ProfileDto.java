package org.orphancare.dashboard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.orphancare.dashboard.entity.Gender;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.validation.NoWhiteSpace;
import org.orphancare.dashboard.validation.ValidGender;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ProfileDto {

    private UUID id;
    private String profilePicture;
    private LocalDate birthday;
    private LocalDate joinDate;
    private LocalDate leaveDate;
    private String bio;
    private boolean isAlumni;
    private boolean isCareTaker;
    private BedRoomDto.ShortResponse bedRoom;

    @NotNull
    private UUID bedRoomId;

    @NotBlank
    private String fullName;

    @Valid
    private AddressDto address;

    @Valid
    private GuardianDto.Response guardian;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull
    @ValidGender
    private String gender;

    @Pattern(regexp = "^\\d{16}$", message = "NIK must be exactly 16 digits")
    @NoWhiteSpace
    private String nikNumber;

    @Pattern(regexp = "^\\d{16}$", message = "KK number must be exactly 16 digits")
    @NoWhiteSpace
    private String kkNumber;

    @NotNull
    private Profile.OrphanType orphanType;

    private String orphanTypeText;

    @Data
    public static class ShortResponse {
        private UUID id;
        private String fullName;
        private String profilePicture;
        private Gender gender;
    }
}
