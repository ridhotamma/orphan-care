package org.orphancare.dashboard.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.orphancare.dashboard.entity.Gender;
import org.orphancare.dashboard.entity.GuardianType;
import org.orphancare.dashboard.entity.Profile;
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

    private UUID bedRoomId;

    @NotBlank
    private String fullName;

    @Valid
    private AddressDto address;

    @Valid
    private GuardianDto.Response guardian;

    private UUID guardianTypeId;

    private GuardianType guardianRelationship;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull
    @ValidGender
    private String gender;

    @Pattern(regexp = "^\\d{16}$", message = "Nik number should be 16 digit")
    @Nullable
    private String nikNumber;

    @Pattern(regexp = "^\\d{16}$", message = "Kk number should be 16 digit")
    @Nullable
    private String kkNumber;

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
