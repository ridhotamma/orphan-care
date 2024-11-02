package org.orphancare.dashboard.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.orphancare.dashboard.entity.Gender;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.entity.RoleType;
import org.orphancare.dashboard.util.RandomGeneratorUtil;
import org.orphancare.dashboard.validation.NoWhiteSpace;
import org.orphancare.dashboard.validation.ValidPassword;
import org.orphancare.dashboard.validation.ValidRoles;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class CreateUserDto {
    @Email
    private String email = RandomGeneratorUtil.generateEmail();

    @Size(min = 8, max = 255)
    @ValidPassword
    private String password = RandomGeneratorUtil.generatePassword(12);;

    @Size(max = 255)
    @NoWhiteSpace
    private String username = RandomGeneratorUtil.generateUsername(8);

    @ValidRoles
    private Set<String> roles = Set.of(RoleType.ROLE_USER.toString());

    private boolean active = false;

    @NotBlank
    private String fullName;

    private LocalDate birthday;

    private LocalDate joinDate;

    private String bio;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull
    private Gender gender;

    @Valid
    private AddressDto address;

    private UUID bedRoomId;

    @Valid
    private GuardianDto guardian;

    private UUID guardianTypeId;

    private boolean isCareTaker = false;

    private boolean isAlumni = false;

    private String profilePicture;

    @Pattern(regexp = "^\\d{16}$", message = "Nik number should be 16 digit")
    @Nullable
    private String nikNumber;

    @Pattern(regexp = "^\\d{16}$", message = "Kk number should be 16 digit")
    @Nullable
    private String kkNumber;

    private Profile.OrphanType orphanType = Profile.OrphanType.POOR;
}
