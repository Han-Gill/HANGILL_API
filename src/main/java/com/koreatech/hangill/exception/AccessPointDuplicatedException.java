package com.koreatech.hangill.exception;

import com.koreatech.hangill.exception.global.DuplicatedException;

public class AccessPointDuplicatedException extends DuplicatedException {
    private static final String DEFAULT_MESSAGE = "공유기가 이미 있습니다!";

    private AccessPointDuplicatedException(String message) {
        super(message);
    }

    public static AccessPointDuplicatedException withDetail(String detail) {
        return new AccessPointDuplicatedException(detail + " " + DEFAULT_MESSAGE);
    }
}
