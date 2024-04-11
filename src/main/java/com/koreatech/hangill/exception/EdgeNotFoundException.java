package com.koreatech.hangill.exception;

import com.koreatech.hangill.exception.global.DataNotFoundException;

public class EdgeNotFoundException extends DataNotFoundException {
    private static final String DEFAULT_MESSAGE = "간선을 찾을 수 없습니다.";
    public EdgeNotFoundException(String message) {
        super(message);
    }

    public static EdgeNotFoundException withDetail(String detail) {
        return new EdgeNotFoundException(DEFAULT_MESSAGE + " " + detail);
    }
}
