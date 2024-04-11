package com.koreatech.hangill.exception;

import com.koreatech.hangill.exception.global.DataNotFoundException;

public class BuildingNotFoundException extends DataNotFoundException {
    private static final String DEFAULT_MESSAGE = "건물을 찾을 수 없습니다.";
    public BuildingNotFoundException(String message) {
        super(message);
    }

    public static BuildingNotFoundException withDetail(String detail) {
        return new BuildingNotFoundException(detail + " " + DEFAULT_MESSAGE);
    }
}
