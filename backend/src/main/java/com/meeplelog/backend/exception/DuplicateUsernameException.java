package com.meeplelog.backend.exception;

public class DuplicateUsernameException extends BusinessException {
    public DuplicateUsernameException() {
    }

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
