package com.meeplelog.backend.exception;

public class PasswordMismatchException extends BusinessException {
    public PasswordMismatchException() {
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}
