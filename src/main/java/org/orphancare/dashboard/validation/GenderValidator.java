package org.orphancare.dashboard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.orphancare.dashboard.entity.Gender;

import java.util.Arrays;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return Arrays.stream(Gender.values())
                .anyMatch(gender -> gender.name().equalsIgnoreCase(value));
    }
}
