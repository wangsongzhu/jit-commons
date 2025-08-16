package com.nonisystems.jit.common.exception;

public class GeneralException extends RuntimeException {

    private final int code;
    private final String message;

    public GeneralException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "GeneralException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
