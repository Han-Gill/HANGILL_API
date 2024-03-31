package com.koreatech.hangill.exception;

public class NoSuchNodeException extends RuntimeException{
    public NoSuchNodeException(String s) {
        super(s);
    }
    public NoSuchNodeException() {
        super();
    }

    public NoSuchNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchNodeException(Throwable cause) {
        super(cause);
    }

    protected NoSuchNodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
