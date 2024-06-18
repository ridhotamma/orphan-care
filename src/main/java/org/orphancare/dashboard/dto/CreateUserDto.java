package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.orphancare.dashboard.validation.NoWhiteSpace;
import org.orphancare.dashboard.validation.ValidPassword;
import org.orphancare.dashboard.validation.ValidRoles;

import java.util.Set;

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

    private boolean active;
}
