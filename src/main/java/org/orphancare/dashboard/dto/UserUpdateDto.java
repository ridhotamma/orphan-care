package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, message = "Username must be at least 4 characters long")
    private String username;

    @NotEmpty
    private Set<String> roles;
}