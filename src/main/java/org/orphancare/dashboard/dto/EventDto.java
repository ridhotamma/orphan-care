package org.orphancare.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orphancare.dashboard.entity.Event;
import org.orphancare.dashboard.validation.DateRange;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DateRange(startDate = "startDate", endDate = "endDate", message = "End date must be after start date")
public class EventDto {
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Start date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "Organizer is required")
    private String organizer;

    @NotNull(message = "Status is required")
    private Event.EventStatus status;

    @NotBlank(message = "Organizer phone number is required")
    private String organizerPhoneNumber;

    @NotBlank(message = "Place is required")
    private String place;
}
