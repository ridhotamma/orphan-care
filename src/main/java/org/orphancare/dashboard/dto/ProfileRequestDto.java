package org.orphancare.dashboard.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileRequestDto {
    private String fullName;
    private String profilePicture;
    private LocalDate birthday;
    private LocalDate joinDate;
    private LocalDate leaveDate;
    private String bio;
    private AddressRequestDto address;
}
