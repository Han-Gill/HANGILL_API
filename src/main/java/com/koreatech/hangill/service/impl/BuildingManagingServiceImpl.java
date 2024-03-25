package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.domain.Edge;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.*;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.repository.EdgeRepository;
import com.koreatech.hangill.service.BuildingManagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 건물 데이터 삽입시 사용하는 Service
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuildingManagingServiceImpl implements BuildingManagingService {
    private final BuildingRepository buildingRepository;
    private final NodeServiceImpl nodeService;
    private final EdgeRepository edgeRepository;

    /**
     * 건물을 저장
     * @param request : 건물 id, 건물 이름, 건물 설명, 건물 위도, 경도
     * @return 저장한 건물 id
     */
    @Override
    public Long saveBuilding(CreateBuildingRequest request) {
        Building building = new Building(request);
        validateDuplicatedBuilding(building.getName());
        buildingRepository.save(building);
        return building.getId();
    }

    /**
     * 동일 이름의 건물이 있는지 검증하는 로직
     */
    public void validateDuplicatedBuilding(String name) {
        if (buildingRepository.findAll(name).size() > 0) throw new IllegalStateException("같은 이름의 건물이 있습니다.");
    }

    /**
     * 건물에 노드 추가
     * @param request : 건물 id, 추가할 노드 정보
     */

    @Override
    public void addNode(AddNodeToBuildingRequest request) {
        Building building = buildingRepository.findOne(request.getBuildingId());
        nodeService.validateDuplicatedNode(
                new NodeSearch(
                        building.getId(),
                        request.getCreateNodeRequest().getNumber(),
                        request.getCreateNodeRequest().getFloor())
        );
        Node node = new Node(request.getCreateNodeRequest());
        building.addNode(node);
    }

    /**
     * 건물에 엣지 추가
     * @param request : 건물 id, 추가할 엣지 정보(출발 노드 번호, 출발 노드 층, 도착 노드 번호, 도착 노드 층)
     */
    @Override
    public void addEdge(AddEdgeToBuildingRequest request) {
        Building building = buildingRepository.findOne(request.getBuildingId());
        // 먼저 연결할 Node 를 영속화해야함.
        Node startNode = nodeService.findOne(new NodeSearch(
                building.getId(),
                request.getStartNodeNumber(),
                request.getStartNodeFloor()
        ));

        Node endNode = nodeService.findOne(new NodeSearch(
                building.getId(),
                request.getEndNodeNumber(),
                request.getEndNodeFloor()
        ));
        building.addEdge(Edge.createEdge(startNode, endNode, request.getDistance()));
    }

    public void deleteEdge(DeleteEdgeRequest request) {

    }

    public void deleteNode(DeleteNodeRequest request) {

    }

    /**
     * 건물의 노드 번호로 구성된 그래프 반환.
     * @param id : 건물 id
     */
    public Map<Long, List<Long[]>> findNumberGraph(Long id) {

        Building building = buildingRepository.findOne(id);
        List<Node> nodes = building.getNodes();
        List<Edge> edges = edgeRepository.findAll(id);

        Map<Long, List<Long[]>> graph = new HashMap<>();
        for (Node node : nodes) {
            graph.put(Long.valueOf(node.getNumber()), new ArrayList<>());
        }
        for (Edge edge : edges) {
            Long start = Long.valueOf(edge.getStartNode().getNumber());
            Long end = Long.valueOf(edge.getEndNode().getNumber());
            Long weight = edge.getDistance();
            graph.get(start).add(new Long[]{end, weight});
        }
        return graph;
    }





}
