package com.OnlineExaminationSystem.App.entity.validation;



import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SemestNumValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SemestNum {

    String message() default "Must be 1 or 2";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};


}
