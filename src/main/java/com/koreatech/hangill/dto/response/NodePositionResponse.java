package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Node;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NodePositionResponse {
    public NodePositionResponse(Node node) {
        this.number = node.getNumber();
        this.floor = node.getFloor();
    }
    @Schema(description = "노드의 번호", example = "1")
    private Integer number;
    @Schema(description = "노드의 층수", example = "3")
    private Integer floor;
}
