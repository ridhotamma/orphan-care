package org.orphancare.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.orphancare.dashboard.validation.ValidPassword;

import java.util.UUID;

@Data
public class UserPasswordChangeDto {
    @Size(min = 8, max = 255)
    @ValidPassword
    private String oldPassword;

    @Size(min = 8, max = 255)
    @ValidPassword
    private String newPassword;

    @Data
    public static class AdminChange {

        @NotNull
        @NotBlank
        private UUID userId;

        @Size(min = 8, max = 255)
        @ValidPassword
        private String newPassword;
    }
}
