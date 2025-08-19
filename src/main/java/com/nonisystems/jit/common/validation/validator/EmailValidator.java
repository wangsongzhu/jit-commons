package com.nonisystems.jit.common.validation.validator;

import com.nonisystems.jit.common.validation.ValidEmail;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // required
        if (StringUtils.isEmpty(email) || StringUtils.isBlank(email)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.email.required}").addConstraintViolation();
            return false;
        }

        // min and max length
        if (email.length() < 6 || email.length() > 254) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.email.length}").addConstraintViolation();
            return false;
        }

        // Format
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.email.format}").addConstraintViolation();
            return false;
        }

        return true;
    }
}
