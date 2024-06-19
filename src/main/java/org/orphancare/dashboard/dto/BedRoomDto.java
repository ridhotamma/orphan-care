package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orphancare.dashboard.validation.ValidBedRoom;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedRoomDto {

    private UUID id;

    @NotBlank
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    @ValidBedRoom
    private String bedRoomType;
}
