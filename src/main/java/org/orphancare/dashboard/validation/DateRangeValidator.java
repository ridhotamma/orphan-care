package org.orphancare.dashboard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {
    private String startDateField;
    private String endDateField;

    @Override
    public void initialize(DateRange constraintAnnotation) {
        this.startDateField = constraintAnnotation.startDate();
        this.endDateField = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDate startDate = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(startDateField);
        LocalDate endDate = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(endDateField);

        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null checks
        }

        return !endDate.isBefore(startDate);
    }
}