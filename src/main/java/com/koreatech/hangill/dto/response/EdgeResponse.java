package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Edge;
import lombok.Data;

@Data
public class EdgeResponse {
    public EdgeResponse(Edge edge) {
        this.edgeId = edge.getId();
        this.startNode = new NodeResponse(edge.getStartNode());
        this.endNode = new NodeResponse(edge.getEndNode());
        this.distance = edge.getDistance();
    }
    private Long edgeId;
    private NodeResponse startNode;
    private NodeResponse endNode;
    private Long distance;
}
