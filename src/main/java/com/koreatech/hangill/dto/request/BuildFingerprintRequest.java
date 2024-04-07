package com.koreatech.hangill.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BuildFingerprintRequest {
    private Long nodeId;
    private List<SignalRequest> signals;
}
