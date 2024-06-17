package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.orphancare.dashboard.validation.NoWhiteSpace;

import java.util.Set;

@Data
public class UpdateUserDto {
    @Email
    private String email;

    @Size(max = 255)
    @NoWhiteSpace
    private String username;

    private Set<String> roles;
    private boolean active;
}
