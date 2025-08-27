package com.nonisystems.jit.common.validation;

import com.nonisystems.jit.common.validation.validator.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "Email invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
