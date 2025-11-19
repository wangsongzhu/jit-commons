package com.nonisystems.jit.common.exception;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class ValidationErrorException extends RuntimeException {
    private List<ValidationErrorItem> validationErrors;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    @ToString
    public static class ValidationErrorItem implements Serializable {
        private String fieldName;
        private String messageKey;
        private Object[] args;
    }
}
