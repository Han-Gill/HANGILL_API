package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NodeResponse {
    public NodeResponse(Node node) {
        this.id = node.getId();
        this.number = node.getNumber();
        this.floor = node.getFloor();
        this.type = node.getType();
    }
    private Long id;
    private Integer number;
    private Integer floor;
    private NodeType type;
}
