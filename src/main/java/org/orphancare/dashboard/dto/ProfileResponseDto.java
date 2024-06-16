package org.orphancare.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orphancare.dashboard.entity.Address;
import org.orphancare.dashboard.entity.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

    private UUID id;
    private String profilePicture;
    private String fullName;
    private Date birthday;
    private String bio;
    private Address address;
    private List<Document> documents;
}
