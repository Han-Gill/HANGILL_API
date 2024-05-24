package com.koreatech.hangill.domain;

import com.koreatech.hangill.dto.request.CreateNodeRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;

/**
 * 추후 Setter 제공하지 않게끔 수정 필요
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Node {
    // Entity 초기화가 복잡하지 않다면 DTO로 받는 것보단 멤버 변수를 일일히 나열하는 편이 좋을 듯.
    public Node(CreateNodeRequest request) {
        this.number = request.getNumber();
        this.name = request.getName();
        this.description = request.getDescription();
        this.type = request.getType();
        this.floor = request.getFloor();
    }
    public Node(Integer number, String name, String description, NodeType type, Integer floor) {
        this.number = number;
        this.name = name;
        this.description = description;
        this.type = type;
        this.floor = floor;
    }

    public void updateCoordinates(Integer y, Integer x) {
        this.y = y;
        this.x = x;
    }


    @Id @GeneratedValue(strategy = IDENTITY)
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

    private Integer x;

    private Integer y;

    @OneToMany(mappedBy = "node", cascade = ALL, orphanRemoval = true)
    private List<Fingerprint> fingerprints = new ArrayList<>();

    public void changeBuilding(Building building) {
        this.building = building;
    }

    // 연관 관계 편의 메소드.
    private void addFingerprint(Fingerprint fingerprint) {
        this.fingerprints.add(fingerprint);
        fingerprint.changeNode(this);
    }

    public void buildFingerprints(List<Fingerprint> fingerprints) {
        // 기존의 Fingerprint 모두 제거 : clear()로 리스트를 비워주면 orphanRemoval 옵션으로 인해 연관 관계가 끊어진 Fingerprint Entity가 지워짐
        this.fingerprints.clear();
        for (Fingerprint fingerprint : fingerprints) {
            addFingerprint(fingerprint);
        }
    }
}
