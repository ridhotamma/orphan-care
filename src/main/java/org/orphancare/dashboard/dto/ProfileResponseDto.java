package org.orphancare.dashboard.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ProfileResponseDto {
    private UUID id;
    private String fullName;
    private String profilePicture;
    private LocalDate birthday;
    private LocalDate joinDate;
    private LocalDate leaveDate;
    private String bio;
    private AddressResponseDto address;
}
