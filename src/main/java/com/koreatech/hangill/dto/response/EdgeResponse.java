package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Edge;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class EdgeResponse {
    public EdgeResponse(Edge edge) {
        this.startNode = new NodeResponse(edge.getStartNode());
        this.endNode = new NodeResponse(edge.getEndNode());
        this.distance = edge.getDistance();
    }
    private NodeResponse startNode;
    private NodeResponse endNode;
    private Long distance;
}
