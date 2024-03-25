package com.koreatech.hangill.domain;

import com.koreatech.hangill.dto.request.CreateNodeRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

/**
 * 추후 Setter 제공하지 않게끔 수정 필요
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Node {
    public Node(CreateNodeRequest request) {
        this.number = request.getNumber();
        this.name = request.getName();
        this.description = request.getDescription();
        this.type = request.getType();
        this.floor = request.getFloor();
    }

    @Id
    @GeneratedValue
    @Column(name = "node_id")
    private Long id;

    private Integer number;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private NodeType type;

    private Integer floor;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    public void changeBuilding(Building building) {
        this.building = building;
    }
}
