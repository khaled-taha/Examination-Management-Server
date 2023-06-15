package com.OnlineExaminationSystem.App.entity.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SemestNumValidation implements ConstraintValidator<SemestNum, Integer> {

    @Override
    public boolean isValid(Integer semestNum, ConstraintValidatorContext constraintValidatorContext) {
        if(semestNum != null && (semestNum == 1 || semestNum == 2)) return true;
        else return false;
    }
}
