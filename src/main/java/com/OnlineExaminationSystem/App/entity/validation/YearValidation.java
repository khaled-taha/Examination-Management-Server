package com.OnlineExaminationSystem.App.entity.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YearValidation implements ConstraintValidator<Year, Integer> {

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext constraintValidatorContext) {
        if(year != null && (year >= 1 && year <= 4)) return true;
        else return false;
    }
}
