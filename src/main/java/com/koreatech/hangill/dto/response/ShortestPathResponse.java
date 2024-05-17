package com.koreatech.hangill.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShortestPathResponse {

    @Schema(
            description = "경로의 총 길이(mm)",
            example = "1,000,320"
    )
    private Long totalDistance;
    @Schema(
            description = "경로상에서 지나는 노드의 순서"
    )
    private List<NodeResponse> path;
}
