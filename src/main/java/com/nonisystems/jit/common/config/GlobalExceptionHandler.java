package com.nonisystems.jit.common.config;

import com.nonisystems.jit.common.constant.GlobalConstant;
import com.nonisystems.jit.common.dto.ApiErrors;
import com.nonisystems.jit.common.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Validate input
     *
     * @param ex MethodArgumentNotValidException
     * @return ApiErrors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrors> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Locale currentLocale = LocaleContextHolder.getLocale();
        log.debug("currentLocale: {}", currentLocale);

        ApiErrors apiErrors = new ApiErrors();
        apiErrors.setStatus(HttpStatus.BAD_REQUEST.value());
        apiErrors.setError(HttpStatus.BAD_REQUEST.name());
        String errorMessage = messageSource.getMessage(GlobalConstant.VALIDATION_INPUT_INVALID, null, currentLocale);
        apiErrors.setMessage(errorMessage);
        apiErrors.setFieldErrors(errors);
        return new ResponseEntity<>(apiErrors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle common business exception
     *
     * @param ex GeneralException
     * @return ApiErrors
     */
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiErrors> handleValidationExceptions(GeneralException ex) {
        if (log.isDebugEnabled()) {
            log.debug("GeneralException: {}", ex.toString());
        }
        ApiErrors apiErrors = new ApiErrors();
        apiErrors.setStatus(ex.getCode());
        apiErrors.setMessage(ex.getMessage());

        HttpStatus httpStatus = HttpStatus.PROCESSING;
        if (ex.getCode() == 400) {
            httpStatus = HttpStatus.BAD_REQUEST;
            apiErrors.setError(HttpStatus.BAD_REQUEST.name());
        } else if (ex.getCode() == 500) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            apiErrors.setError(HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
        return new ResponseEntity<>(apiErrors, httpStatus);
    }
}
