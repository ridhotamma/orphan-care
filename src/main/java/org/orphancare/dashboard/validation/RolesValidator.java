package org.orphancare.dashboard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class RolesValidator implements ConstraintValidator<ValidRoles, Set<String>> {

    private final Set<String> allowedRoles = new HashSet<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));

    @Override
    public boolean isValid(Set<String> roles, ConstraintValidatorContext context) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        for (String role : roles) {
            if (!allowedRoles.contains(role)) {
                return false;
            }
        }
        return true;
    }
}
