package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NodeDetailResponse {
    public NodeDetailResponse(Node node) {
        this.building = node.getBuilding().getName();
        this.name = node.getName();
        this.floor = node.getFloor();
        this.type = node.getType();
        this.description = node.getDescription();
        this.number = node.getNumber();
    }

    @Schema(description = "건물 이름", example = "공학 2관")
    private String building;
    @Schema(description = "노드 이름", example = "319호")
    private String name;
    @Schema(description = "노드 타입", example = "ROOM")
    private NodeType type;
    @Schema(description = "노드 세부 설명", example = "XXX 교수님 연구실")
    private String description;
    @Schema(description = "노드 번호", example = "2")
    private Integer number;
    @Schema(description = "노드 층수", example = "3")
    private Integer floor;
}
