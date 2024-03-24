package com.koreatech.hangill.domain;


import com.koreatech.hangill.dto.request.CreateBuildingRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Building {
    public Building(CreateBuildingRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
    }

    @Id
    @GeneratedValue
    @Column(name = "building_id")
    private Long id;

    private String name;

    private String description;

    @Column(precision = 18, scale = 10)
    private BigDecimal latitude;
    @Column(precision = 18, scale = 10)
    private BigDecimal longitude;


    @OneToMany(mappedBy = "building", cascade = ALL)
    private List<Node> nodes = new ArrayList<>();


    /**
     * 이거 조회시 사용할 경우 쿼리 너무 많이 나감. 성능 생각해서 조회시 사용하지 말자
     */
    @OneToMany(mappedBy = "building", cascade = ALL)
    private List<Edge> edges = new ArrayList<>();

    // 양방향 연관 관계 편의 메소드.
    public void addNode(Node node) {
        node.changeBuilding(this);
        this.nodes.add(node);
    }
    // 양방향 연관 관계 편의 메소드.
    public void addEdge(Edge edge) {
        edge.changeBuilding(this);
        this.edges.add(edge);
    }

    // 그래프 생성.

//    /**
//     * 추후 Fetch Join으로 최적화 고려
//     */
//    public Map<Long, List<Long[]>> createGraph() {
//        Map<Long, List<Long[]>> graph = new HashMap<>();
//        for (Node node : nodes) {
//            graph.put(node.getId(), new ArrayList<>());
//        }
//        for (Edge edge : edges) {
//            Long start = Long.valueOf(edge.getStartNode().getNumber());
//            Long end = Long.valueOf(edge.getEndNode().getNumber());
//            Long weight = edge.getDistance();
//            graph.get(start).add(new Long[]{end, weight});
//            graph.get(end).add(new Long[]{start, weight});
//        }
//        return graph;
//    }


}
