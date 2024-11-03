package org.orphancare.dashboard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SettingsDto {
    private UUID id;

    @NotNull(message = "Child submission setting is required")
    private Boolean enableChildSubmission;

    @NotNull(message = "Donation portal setting is required")
    private Boolean enableDonationPortal;

    @NotNull
    @Valid
    private List<BankAccountDto> bankAccounts;

    @NotNull(message = "Organization phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Please enter a valid phone number (8-15 digits, may start with +)")
    private String orgPhoneNumber;

    @Data
    public static class BankAccountDto {
        private UUID id;

        @NotBlank(message = "Bank name is required")
        @Size(max = 100, message = "Bank name cannot exceed 100 characters")
        private String bankName;

        @NotBlank(message = "Bank account number is required")
        @Size(max = 50, message = "Bank account number cannot exceed 50 characters")
        @Pattern(regexp = "^[0-9-]*$", message = "Bank account number can only contain numbers and hyphens")
        private String bankAccountNumber;

        @NotBlank(message = "Bank branch is required")
        @Size(max = 100, message = "Bank branch cannot exceed 100 characters")
        private String bankBranch;

        @NotBlank(message = "Account holder name is required")
        @Size(max = 255, message = "Account holder name cannot exceed 255 characters")
        @Pattern(regexp = "^[a-zA-Z\\s'-]*$", message = "Account holder name can only contain letters, spaces, hyphens and apostrophes")
        private String accountHolderName;
    }
}
