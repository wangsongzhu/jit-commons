package com.nonisystems.jit.common.exception;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class GeneralException extends RuntimeException {
    private int code;
    private String message;
}
