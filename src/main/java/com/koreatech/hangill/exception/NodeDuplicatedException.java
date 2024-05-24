package com.koreatech.hangill.exception;

import com.koreatech.hangill.exception.global.DuplicatedException;

public class NodeDuplicatedException extends DuplicatedException {
    private static final String DEFAULT_MESSAGE = "노드가 이미 있습니다!";

    private NodeDuplicatedException(String message) {
        super(message);
    }

    public static NodeDuplicatedException withDetail(String detail) {
        return new NodeDuplicatedException(detail + " " + DEFAULT_MESSAGE);
    }
}
