package com.meeplelog.backend.exception;

public class EventGameRequiredException extends BusinessException {
    public EventGameRequiredException() {
    }

    public EventGameRequiredException(String message) {
        super(message);
    }
}
