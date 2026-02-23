package com.meeplelog.backend.exception;

public class DuplicateNameException extends BusinessException {
    public DuplicateNameException() {
    }

    public DuplicateNameException(String message) {
        super(message);
    }
}
