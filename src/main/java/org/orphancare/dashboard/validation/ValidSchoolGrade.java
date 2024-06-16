package org.orphancare.dashboard.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SchoolGradeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSchoolGrade {
    String message() default "Invalid school grade for the given school type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
