package com.koreatech.hangill.dto;

import com.koreatech.hangill.domain.Node;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NodeDto {
    public NodeDto(Node node) {
        this.id = node.getId();
        this.number = node.getNumber();
        this.floor = node.getFloor();
        this.name = node.getName();
        this.description = node.getDescription();
    }
    @Schema(
            description = "노드의 ID",
            example = "1"
    )
    private Long id;
    @Schema(
            description = "건물 지도상 노드의 번호",
            example = "2"
    )
    private Integer number;
    @Schema(
            description = "건물 내 노드의 층 수",
            example = "3"
    )
    private Integer floor;
    @Schema(
            description = "노드의 이름",
            example = "319호"
    )
    private String name;

    @Schema(
            description = "노드의 설명",
            example = "XXX 교수님 연구실"
    )
    private String description;
}
