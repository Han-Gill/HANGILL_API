package com.koreatech.hangill.exception;

import com.koreatech.hangill.exception.global.DataNotFoundException;

public class NodeNotFoundException extends DataNotFoundException {
    private static final String DEFAULT_MESSAGE = "노드를 찾을 수 없습니다.";
    public NodeNotFoundException(String s) {
        super(s);
    }

    public static NodeNotFoundException withDetail(String detail) {
        return new NodeNotFoundException(detail + " " + DEFAULT_MESSAGE);
    }
}
