package org.orphancare.dashboard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.orphancare.dashboard.entity.BedRoomType;

import java.util.Arrays;

public class BedRoomValidator implements ConstraintValidator<ValidBedRoom, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return Arrays.stream(BedRoomType.values())
                .anyMatch(room -> room.name().equalsIgnoreCase(value));
    }
}
