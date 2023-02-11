package com.OnlineExaminationSystem.App.entity.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = YearValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Year {

    String message() default "Must be in range 1 : 4";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
