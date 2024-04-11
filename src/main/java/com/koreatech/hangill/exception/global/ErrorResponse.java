package com.koreatech.hangill.exception.global;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String message;

    private ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse from(String message) {
        return new ErrorResponse(message);
    }
}
