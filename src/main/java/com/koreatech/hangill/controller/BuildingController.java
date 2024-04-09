package com.koreatech.hangill.controller;

import com.koreatech.hangill.controller.swagger.BuildingApi;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import com.koreatech.hangill.dto.NodeDto;
import com.koreatech.hangill.dto.request.ShortestPathRequest;
import com.koreatech.hangill.dto.response.NodeDtosResponse;
import com.koreatech.hangill.dto.response.ShortestPathResponse;
import com.koreatech.hangill.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuildingController implements BuildingApi {
    private final BuildingService buildingService;

    @GetMapping("/api/v1/building/nodes/{buildingId}/{nodeType}")
    public ResponseEntity<NodeDtosResponse> findNodesByType(
            @PathVariable Long buildingId, @PathVariable NodeType nodeType) {
            List<Node> nodes = buildingService.findAllNodesByType(buildingId, nodeType);
            List<NodeDto> nodeDtos = nodes.stream()
                    .map(NodeDto::new)
                    .toList();
            return ResponseEntity.ok().body(new NodeDtosResponse(nodeDtos));
    }

    @GetMapping("/api/v1/building/path/{buildingId}/{startNodeId}/{endNodeId}")
    public ResponseEntity<ShortestPathResponse> findPath(
            @PathVariable Long buildingId,
            @PathVariable Long startNodeId,
            @PathVariable Long endNodeId) {
            ShortestPathResponse path = buildingService.findPath(
                    new ShortestPathRequest(buildingId, startNodeId, endNodeId)
            );
            return ResponseEntity.ok().body(path);

    }
}
