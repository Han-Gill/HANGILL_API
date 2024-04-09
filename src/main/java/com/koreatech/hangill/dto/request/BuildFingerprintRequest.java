package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BuildFingerprintRequest {
    @NotNull(message = "노드 ID 반드시 추가!")
    @Schema(
            description = "핑거프린트를 추가할 노드 ID",
            nullable = false,
            example = "1"
    )
    private Long nodeId;

    @Schema(
            description = "노드에 추가할 wifi 신호들",
            nullable = true,
            extensions = {}
    )
    private List<SignalRequest> signals;
}
