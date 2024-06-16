package org.orphancare.dashboard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoWhiteSpaceValidator implements ConstraintValidator<NoWhiteSpace, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotBlank should handle null case
        }
        return !value.contains(" ");
    }
}
