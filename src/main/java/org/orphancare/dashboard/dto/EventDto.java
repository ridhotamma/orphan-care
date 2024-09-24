package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orphancare.dashboard.entity.Event;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private UUID id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotBlank
    @NotNull
    private String organizer;

    @NotNull
    private Event.EventStatus status;

    @NotBlank
    @NotNull
    private String organizerPhoneNumber;

    @NotBlank
    @NotNull
    private String place;
}