package org.orphancare.dashboard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.entity.SchoolGrade;
import org.orphancare.dashboard.entity.SchoolType;

public class SchoolGradeValidator implements ConstraintValidator<ValidSchoolGrade, Profile> {

    @Override
    public boolean isValid(Profile profile, ConstraintValidatorContext context) {
        if (profile == null || profile.getSchoolType() == null || profile.getSchoolGrade() == null) {
            return true;
        }

        SchoolType schoolType = profile.getSchoolType();
        SchoolGrade schoolGrade = profile.getSchoolGrade();

        return switch (schoolType) {
            case ELEMENTARY -> schoolGrade == SchoolGrade.GRADE_1 ||
                    schoolGrade == SchoolGrade.GRADE_2 ||
                    schoolGrade == SchoolGrade.GRADE_3 ||
                    schoolGrade == SchoolGrade.GRADE_4 ||
                    schoolGrade == SchoolGrade.GRADE_5 ||
                    schoolGrade == SchoolGrade.GRADE_6;
            case JUNIOR_HIGH_SCHOOL, SENIOR_HIGH_SCHOOL -> schoolGrade == SchoolGrade.GRADE_1 ||
                    schoolGrade == SchoolGrade.GRADE_2 ||
                    schoolGrade == SchoolGrade.GRADE_3;
        };
    }
}
