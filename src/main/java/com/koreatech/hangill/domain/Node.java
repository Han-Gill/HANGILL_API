package com.koreatech.hangill.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

/**
 * 추후 Setter 제공하지 않게끔 수정 필요
 */
@Entity
@Getter @Setter
public class Node {
    @Id @GeneratedValue
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
