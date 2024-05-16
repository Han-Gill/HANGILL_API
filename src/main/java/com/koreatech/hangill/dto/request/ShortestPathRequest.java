package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortestPathRequest {
    @NotNull(message = "건물 ID는 필수로 입력해주세요")
    @Schema(
            description = "경로를 조회할 건물 ID",
            example = "1"
    )
    private Long buildingId;

    @NotNull(message = "시작 노드 번호는 필수로 입력해주세요")
    @Schema(
            description = "경로의 시작 노드 번호",
            example = "1"
    )
    private Integer startNodeNumber;

    @NotNull(message = "시작 노드 층 수는 필수로 입력해주세요")
    @Schema(
            description = "경로의 시작 노드 층",
            example = "1"
    )
    private Integer startNodeFloor;

    @NotNull(message = "도착 노드 번호는 필수로 입력해주세요")
    @Schema(
            description = "경로의 도착 노드 번호",
            example = "76"
    )
    private Integer endNodeNumber;

    @NotNull(message = "도착 노드의 층수는 필수로 입력해주세요")
    @Schema(
            description = "경로의 도착 노드 층",
            example = "2"
    )
    private Integer endNodeFloor;

}
