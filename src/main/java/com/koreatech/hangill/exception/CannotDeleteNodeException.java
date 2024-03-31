package com.koreatech.hangill.exception;

public class CannotDeleteNodeException extends RuntimeException {
    public CannotDeleteNodeException() {

    }

    public CannotDeleteNodeException(String message) {
        super(message);
    }

    public CannotDeleteNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotDeleteNodeException(Throwable cause) {
        super(cause);
    }

    protected CannotDeleteNodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
