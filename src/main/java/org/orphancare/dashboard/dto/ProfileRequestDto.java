package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orphancare.dashboard.entity.Address;
import org.orphancare.dashboard.entity.SchoolGrade;
import org.orphancare.dashboard.entity.SchoolType;
import org.orphancare.dashboard.validation.ValidSchoolGrade;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidSchoolGrade
public class ProfileRequestDto {

    @Size(max = 255)
    private String profilePicture;

    @Size(max = 1000)
    private String bio;

    private Address address;

    private Set<UUID> documentIds;

    private SchoolGrade schoolGrade;

    private SchoolType schoolType;
}
