package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.orphancare.dashboard.entity.Address;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {

    @Size(max = 255)
    private String fullName;

    private Date birthday;

    @Size(max = 255)
    private String profilePicture;

    @Size(max = 1000)
    private String bio;

    private Address address;

    private Set<UUID> documentIds;
}
