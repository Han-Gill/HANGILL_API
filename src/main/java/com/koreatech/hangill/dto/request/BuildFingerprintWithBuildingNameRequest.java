package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class BuildFingerprintWithBuildingNameRequest {
    private NodeSearchRequest node;

    @Schema(
            description = "노드에 추가할 wifi 신호들",
            nullable = true,
            extensions = {}
    )
    private List<SignalRequest> signals;
}
