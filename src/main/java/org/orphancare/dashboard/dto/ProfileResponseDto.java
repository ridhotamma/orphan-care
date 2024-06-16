package org.orphancare.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orphancare.dashboard.entity.Address;
import org.orphancare.dashboard.entity.Document;
import org.orphancare.dashboard.entity.SchoolGrade;
import org.orphancare.dashboard.entity.SchoolType;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

    private UUID id;
    private String profilePicture;
    private String bio;
    private Address address;
    private Set<Document> documents;
    private SchoolGrade schoolGrade;
    private SchoolType schoolType;
}
