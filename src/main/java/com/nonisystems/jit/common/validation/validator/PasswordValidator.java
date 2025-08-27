package com.nonisystems.jit.common.validation.validator;

import com.nonisystems.jit.common.validation.ValidPassword;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // required
        if (StringUtils.isEmpty(password) || StringUtils.isBlank(password)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.password.required}").addConstraintViolation();
            return false;
        }

        // Format
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,64}$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.password.format}").addConstraintViolation();
            return false;
        }

        return true;
    }
}
