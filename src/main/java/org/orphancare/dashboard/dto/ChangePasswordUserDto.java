package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.orphancare.dashboard.validation.ValidPassword;

@Data
public class ChangePasswordUserDto {
    @Size(min = 8, max = 255)
    @ValidPassword
    private String oldPassword;

    @Size(min = 8, max = 255)
    @ValidPassword
    private String newPassword;
}
