package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortestPathRequest {
    @Schema(
            description = "경로를 조회할 건물 ID",
            example = "1"
    )
    private Long buildingId;
    @Schema(
            description = "경로의 시작 노드 ID",
            example = "1"
    )
    private Long startNodeId;
    @Schema(
            description = "경로의 도착 노드 ID",
            example = "76"
    )
    private Long endNodeId;
}
