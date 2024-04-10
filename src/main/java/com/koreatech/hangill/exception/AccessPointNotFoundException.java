package com.koreatech.hangill.exception;

import com.koreatech.hangill.exception.global.DataNotFoundException;

public class AccessPointNotFoundException extends DataNotFoundException {
    private static final String DEFAULT_MESSAGE = "공유기를 찾을 수 없습니다.";
    public AccessPointNotFoundException(String message) {
        super(message);
    }

    public static AccessPointNotFoundException withDetail(String detail) {
        return new AccessPointNotFoundException(DEFAULT_MESSAGE + " " + detail);
    }


}
