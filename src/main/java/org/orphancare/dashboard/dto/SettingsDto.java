package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingsDto {
    private UUID id;

    @NotNull(message = "Enable child submission setting is required")
    private Boolean enableChildSubmission;

    @NotNull(message = "Enable donation portal setting is required")
    private Boolean enableDonationPortal;

    @NotEmpty(message = "At least one bank account number is required")
    private Set<String> bankAccountNumbers;

    @NotBlank(message = "Organization phone number is required")
    private String orgPhoneNumber;
}
