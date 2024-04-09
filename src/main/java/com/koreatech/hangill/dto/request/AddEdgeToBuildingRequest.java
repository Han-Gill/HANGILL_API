package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddEdgeToBuildingRequest {
    @Schema(
            description = "간선을 추가할 건물 ID",
            nullable = false,
            example = "1"
    )
    private Long buildingId;
    @Schema(
            description = "간선의 시작 노드 번호",
            nullable = false,
            example = "1"
    )
    private Integer startNodeNumber;
    @Schema(
            description = "간선의 시작 노드의 층",
            nullable = false,
            example = "2"
    )
    private Integer startNodeFloor;
    @Schema(
            description = "간선의 도착 노드 번호",
            nullable = false,
            example = "2"
    )
    private Integer endNodeNumber;
    @Schema(
            description = "간선의 도착 노드 층",
            nullable = false,
            example = "1"
    )
    private Integer endNodeFloor;
    @Schema(
            description = "간선의 거리",
            nullable = false,
            example = "1021"
    )
    private Long distance;
}
