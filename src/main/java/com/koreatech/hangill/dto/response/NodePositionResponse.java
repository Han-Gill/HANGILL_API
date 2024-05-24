package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NodePositionResponse {
    public NodePositionResponse(Node node) {
        this.number = node.getNumber();
        this.floor = node.getFloor();
        this.type = node.getType();
    }
    @Schema(description = "노드의 번호", example = "1")
    private Integer number;
    @Schema(description = "노드의 층수", example = "3")
    private Integer floor;

    @Schema(description = "노드의 종류", example = "ROAD")
    private NodeType type;
}
