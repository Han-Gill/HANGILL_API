package com.koreatech.hangill.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class ShortestPathResponse {
    public ShortestPathResponse(Long totalDistance, String path) {
        this.path = Stream.of(path.split(" "))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
        this.totalDistance = totalDistance;
    }
    @Schema(
            description = "경로의 총 길이(mm)",
            example = "1,000,320"
    )
    private Long totalDistance;
    @Schema(
            description = "경로상에서 지나는 노드의 순서",
            example = "[1, 2, 5, 4, 3]"
    )
    private List<Long> path;
}
