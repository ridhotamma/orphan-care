package org.orphancare.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String email;
    private String username;
    private Set<String> roles;
    private String password;
    private ProfileRequestDto profile;
    private AddressRequestDto address;
}
