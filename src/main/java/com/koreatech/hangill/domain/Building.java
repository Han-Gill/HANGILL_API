package com.koreatech.hangill.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter @Setter
public class Building {
    @Id @GeneratedValue
    @Column(name = "building_id")
    private Long id;

    private String name;

    private String description;

    @Column(precision = 18, scale = 10)
    private BigDecimal latitude;
    @Column(precision = 18, scale = 10)
    private BigDecimal longitude;

    // Edge의 생명주기를 Building에서 관리
    @OneToMany(mappedBy = "building", cascade = ALL)
    private List<Edge> edges = new ArrayList<>();

    // Node의 생명 주기를 Building에서 관리
    @OneToMany(mappedBy = "building", cascade = ALL)
    private List<Node> nodes = new ArrayList<>();


    // 양방향 연관 관계 편의 메소드
    public void addEdge(Edge edge) {
        this.edges.add(edge);
        edge.changeBuilding(this);
    }
    // 양방향 연관 관계 편의 메소드
    public void addNode(Node node) {
        this.nodes.add(node);
        node.changeBuilding(this);
    }

    // 그래프 생성.

    /**
     * 추후 Fetch Join으로 최적화 고려
     */
    public Map<Long, List<Long[]>> createGraph() {
        Map<Long, List<Long[]>> graph = new HashMap<>();
        for (Node node : nodes) {
            graph.put(node.getId(), new ArrayList<>());
        }
        for (Edge edge : edges) {
            Long start = edge.getStartNode().getId();
            Long end = edge.getEndNode().getId();
            Long weight = edge.getDistance();
            graph.get(start).add(new Long[] {end, weight});
            graph.get(end).add(new Long[] {start, weight});
        }
        return graph;
    }


}
