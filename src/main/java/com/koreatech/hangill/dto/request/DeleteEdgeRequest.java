package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteEdgeRequest {
    @NotNull(message = "반드시 건물 id 입력")
    @Schema(
            description = "간선을 삭제할 건물 ID",
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
}
