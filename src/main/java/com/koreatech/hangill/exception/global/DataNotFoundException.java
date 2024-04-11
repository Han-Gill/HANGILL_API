package com.koreatech.hangill.exception.global;

public abstract class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message) {
        super(message);
    }

}
