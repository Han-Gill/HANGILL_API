package com.koreatech.hangill.dto;

import com.koreatech.hangill.domain.Node;
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
    private Long id;
    private Integer number;
    private Integer floor;
    private String name;
    private String description;
}
