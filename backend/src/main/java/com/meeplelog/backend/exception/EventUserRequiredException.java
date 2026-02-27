package com.meeplelog.backend.exception;

public class EventUserRequiredException extends BusinessException {
    public EventUserRequiredException() {
    }

    public EventUserRequiredException(String message) {
        super(message);
    }
}
