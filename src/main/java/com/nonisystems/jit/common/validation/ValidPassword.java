package com.nonisystems.jit.common.validation;

import com.nonisystems.jit.common.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
