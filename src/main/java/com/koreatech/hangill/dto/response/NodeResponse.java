package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NodeResponse {
    public NodeResponse(Node node) {
        this.id = node.getId();
        this.number = node.getNumber();
        this.floor = node.getFloor();
        this.type = node.getType();
    }
    @Schema(
            description = "노드의 ID",
            example = "1"
    )
    private Long id;
    @Schema(
            description = "지도 상의 노드 번호",
            example = "2"
    )
    private Integer number;

    @Schema(
            description = "건물에서 노드의 층 수",
            example = "1"
    )
    private Integer floor;
    @Schema(
            description = "노드의 타입",
            example = "ENTRANCE"
    )
    private NodeType type;
}
