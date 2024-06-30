package org.orphancare.dashboard.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String email;
    private String username;
    private Set<String> roles;
    private boolean active;

    @Data
    public static class UserWithProfileDto {
        private UUID id;
        private String email;
        private String username;
        private Set<String> roles;
        private boolean active;
        private ProfileDto profile;
    }
}