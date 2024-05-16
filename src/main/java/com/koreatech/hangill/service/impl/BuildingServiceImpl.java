package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.domain.Edge;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.ShortestPathRequest;
import com.koreatech.hangill.dto.response.NodePositionResponse;
import com.koreatech.hangill.dto.response.ShortestPathResponse;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.repository.EdgeRepository;
import com.koreatech.hangill.repository.NodeRepository;
import com.koreatech.hangill.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;
    private final EdgeRepository edgeRepository;
    private final NodeRepository nodeRepository;


    /**
     * 건물에 해당하는 모든 노드 조회
     *
     * @param id : 건물 id
     */
    public List<Node> findAllNodes(Long id) {
        return buildingRepository.findOne(id).getNodes();
    }

    public List<Edge> findAllEdges(Long id) {
        return edgeRepository.findAll(id);
    }

    /**
     * 건물의 노드 목록을 노드 타입을 통해 얻기
     *
     * @param id   : 건물 id
     * @param type : 조회할 노드 타입
     */
    public List<Node> findAllNodesByType(Long id, NodeType type) {
        Building building = buildingRepository.findOne(id);
        return building.getNodes().stream()
                .filter(node -> node.getType() == type)
                .collect(Collectors.toList());
    }

    /**
     * 건물의 노드 id로 구성된 Graph를 반환
     *
     * @param id 건물 id
     */
    public Map<Long, List<Long[]>> findIdGraph(Long id) {
        Building building = buildingRepository.findOne(id);
        List<Node> nodes = building.getNodes();
        List<Edge> edges = edgeRepository.findAll(id);

        Map<Long, List<Long[]>> graph = new HashMap<>();
        for (Node node : nodes) {
            graph.put(node.getId(), new ArrayList<>());
        }
        for (Edge edge : edges) {
            Long start = edge.getStartNode().getId();
            Long end = edge.getEndNode().getId();
            Long weight = edge.getDistance();
            graph.get(start).add(new Long[]{end, weight});
        }
        return graph;
    }


    /**
     * 최단 경로 알고리즘.
     *
     * @param request : 건물 id, 출발 노드 정보(번호, 층), 도착 노드 정보(번호, 층)
     * @return 최단 경로와 거리를 담은 객체
     */
    public ShortestPathResponse findPath(ShortestPathRequest request) {
        Long startNodeId = nodeRepository.findOne(
                new NodeSearch(
                        request.getBuildingId(),
                        request.getStartNodeNumber(),
                        request.getStartNodeFloor())
        ).getId();
        Long endNodeId = nodeRepository.findOne(
                new NodeSearch(
                        request.getBuildingId(),
                        request.getEndNodeNumber(),
                        request.getEndNodeFloor())
        ).getId();

        Long INF = Long.MAX_VALUE;
        List<Node> nodes = buildingRepository.findOne(request.getBuildingId()).getNodes();
        Map<Long, Long> distance = new HashMap<>();
        Map<Long, Long> path = new HashMap<>();
        for (Node node : nodes) {
            distance.put(node.getId(), INF);
        }
        Map<Long, List<Long[]>> graph = findIdGraph(request.getBuildingId());

        distance.put(startNodeId, 0L);
        Queue<Long[]> queue = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        queue.add(new Long[]{startNodeId, 0L});

        while (!queue.isEmpty()) {
            Long[] minNode = queue.poll();
            if (minNode[1] > distance.get(minNode[0])) continue;

            for (Long[] edge : graph.get(minNode[0])) {
                Long n_node = edge[0];
                Long weight = edge[1];
                if (distance.get(minNode[0]) + weight < distance.get(n_node)) {
                    distance.put(n_node, distance.get(minNode[0]) + weight);
                    queue.add(new Long[]{n_node, distance.get(n_node)});
                    path.put(n_node, minNode[0]);
                }
            }
        }


        List<NodePositionResponse> collect = Arrays.stream(reconstructPath(startNodeId, endNodeId, path)
                .split(" "))
                .mapToLong(Long::parseLong)
                .mapToObj(nodeRepository::findOne)
                .map(NodePositionResponse::new)
                .collect(Collectors.toList());

        return new ShortestPathResponse(distance.get(endNodeId), collect);
    }


    private String reconstructPath(Long start, Long node, Map<Long, Long> path) {
        if (node.equals(start)) return start + "";
        return reconstructPath(start, path.get(node), path) + " " + node;
    }


}
