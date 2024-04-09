package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Edge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EdgeResponse {
    public EdgeResponse(Edge edge) {
        this.edgeId = edge.getId();
        this.startNode = new NodeResponse(edge.getStartNode());
        this.endNode = new NodeResponse(edge.getEndNode());
        this.distance = edge.getDistance();
    }

    @Schema(
            description = "간선의 ID",
            example = "1"
    )
    private Long edgeId;
    @Schema(
            description = "간선의 시작 노드"
    )
    private NodeResponse startNode;
    @Schema(
            description = "간선의 도착 노드"

    )
    private NodeResponse endNode;

    @Schema(
            description = "간선의 가중치(노드 사이의 거리(mm))",
            example = "1023"

    )
    private Long distance;
}
