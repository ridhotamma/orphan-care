package org.orphancare.dashboard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.orphancare.dashboard.entity.Gender;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.validation.NoWhiteSpace;
import org.orphancare.dashboard.validation.ValidPassword;
import org.orphancare.dashboard.validation.ValidRoles;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class CreateUserDto {
    @Email
    @NotBlank
    private String email;

    @Size(min = 8, max = 255)
    @ValidPassword
    private String password;

    @Size(max = 255)
    @NotBlank
    @NoWhiteSpace
    private String username;

    @NotEmpty
    @NotNull
    @ValidRoles
    private Set<String> roles;

    @NotNull
    private boolean active;

    @NotBlank
    private String fullName;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private LocalDate joinDate;

    @NotBlank
    private String bio;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull
    private Gender gender;

    @NotNull
    @Valid
    private AddressDto address;

    @NotNull
    private UUID bedRoomId;

    @Valid
    private GuardianDto guardian;

    @NotNull
    private boolean isCareTaker = false;

    @NotNull
    private boolean isAlumni = false;

    private String profilePicture;

    private String nikNumber;

    private String kkNumber;

    private Profile.OrphanType orphanType;
}
