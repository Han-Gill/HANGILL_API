package com.koreatech.hangill.exception;

public class CannotDeleteNodeException extends IllegalStateException {
    public CannotDeleteNodeException(String message) {
        super(message);
    }
}
