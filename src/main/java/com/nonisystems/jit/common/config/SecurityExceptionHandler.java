package com.nonisystems.jit.common.config;

import com.nonisystems.jit.common.constant.GlobalConstant;
import com.nonisystems.jit.common.dto.ApiErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Slf4j
@RestControllerAdvice
@ConditionalOnClass(name = "org.springframework.security.config.annotation.web.builders.HttpSecurity")
public class SecurityExceptionHandler {

    private final MessageSource messageSource;

    public SecurityExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handle AccessDeniedException
     *
     * @param ex AccessDeniedException.class
     * @return ApiErrors
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrors> handleAccessDeniedExceptions(AccessDeniedException ex) {
        if (log.isDebugEnabled()) {
            log.debug("AccessDeniedException: {}", ex.toString());
        }
        Locale currentLocale = LocaleContextHolder.getLocale();
        log.debug("currentLocale: {}", currentLocale);

        ApiErrors apiErrors = new ApiErrors();
        apiErrors.setStatus(HttpStatus.FORBIDDEN.value());
        apiErrors.setError(HttpStatus.FORBIDDEN.name());
        String errorMessage = messageSource.getMessage(GlobalConstant.VALIDATION_ACCESS_DENIED, null, currentLocale);
        apiErrors.setMessage(errorMessage);
        return new ResponseEntity<>(apiErrors, HttpStatus.FORBIDDEN);
    }
}
