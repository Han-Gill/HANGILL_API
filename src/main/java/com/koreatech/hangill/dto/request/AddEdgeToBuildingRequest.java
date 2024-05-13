package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddEdgeToBuildingRequest {
    @NotNull(message = "건물 ID는 반드시 입력해주세요")
    @Schema(
            description = "간선을 추가할 건물 ID",
            nullable = false,
            example = "1"
    )
    private Long buildingId;

    @NotNull(message = "간선의 시작 노드 번호는 반드시 입력해주세요")
    @Schema(
            description = "간선의 시작 노드 번호",
            nullable = false,
            example = "1"
    )
    private Integer startNodeNumber;

    @NotNull(message = "간선의 시작 노드 층은 반드시 입력해주세요")
    @Schema(
            description = "간선의 시작 노드의 층",
            nullable = false,
            example = "2"
    )
    private Integer startNodeFloor;

    @NotNull(message = "간선의 도착 노드 번호는 반드시 입력해주세요")
    @Schema(
            description = "간선의 도착 노드 번호",
            nullable = false,
            example = "2"
    )
    private Integer endNodeNumber;

    @NotNull(message = "간선의 도착 노드 층은 반드시 입력해주세요")
    @Schema(
            description = "간선의 도착 노드 층",
            nullable = false,
            example = "1"
    )

    @NotNull(message = "간선의 거리는 반드시 입력해주세요")
    private Integer endNodeFloor;
    @Schema(
            description = "간선의 거리",
            nullable = false,
            example = "1021"
    )
    private Long distance;

    public static AddEdgeToBuildingRequest oppositeEdge(AddEdgeToBuildingRequest request) {
        return new AddEdgeToBuildingRequest(
                request.getBuildingId(),
                request.getEndNodeNumber(),
                request.getEndNodeFloor(),
                request.getStartNodeNumber(),
                request.getStartNodeFloor(),
                request.getDistance()
        );
    }
}
