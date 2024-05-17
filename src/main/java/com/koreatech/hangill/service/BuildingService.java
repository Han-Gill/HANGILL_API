package com.koreatech.hangill.service;

import com.koreatech.hangill.domain.Edge;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import com.koreatech.hangill.dto.request.ShortestPathRequest;
import com.koreatech.hangill.dto.response.ShortestPathResponse;

import java.util.List;
import java.util.Map;

public interface BuildingService {
    List<Node> findAllNodes(Long id);
    List<Edge> findAllEdges(Long id);
    List<Node> findAllNodesByType(Long id, NodeType type);
    Map<Long, List<Long[]>> findIdGraph(Long id);
    ShortestPathResponse findPath(Long buildingId, Long startNodeId, Long endNodeId);
}
