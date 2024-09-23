package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationDto {
    private UUID id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private LocalDate receivedDate;

    @NotBlank
    @NotNull
    private String receiver;

    @NotBlank
    @NotNull
    private String donatorName;

    @NotNull
    private UUID donationTypeId;
}
