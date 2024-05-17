package com.koreatech.hangill.controller;

import com.koreatech.hangill.controller.swagger.BuildingApi;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import com.koreatech.hangill.dto.NodeDto;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.ShortestPathRequest;
import com.koreatech.hangill.dto.response.NodeDtosResponse;
import com.koreatech.hangill.dto.response.ShortestPathResponse;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.repository.NodeRepository;
import com.koreatech.hangill.service.BuildingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuildingController implements BuildingApi {
    private final BuildingService buildingService;
    private final NodeRepository nodeRepository;
    private final BuildingRepository buildingRepository;

    @GetMapping("/api/v1/building/nodes/{buildingId}/{nodeType}")
    public ResponseEntity<NodeDtosResponse> findNodesByType(
            @PathVariable Long buildingId, @PathVariable NodeType nodeType) {
        List<Node> nodes = buildingService.findAllNodesByType(buildingId, nodeType);
        List<NodeDto> nodeDtos = nodes.stream()
                .map(NodeDto::new)
                .toList();
        return ResponseEntity.ok().body(new NodeDtosResponse(nodeDtos));
    }

    @PostMapping("/api/v1/building/path")
    public ResponseEntity<ShortestPathResponse> findPath(@RequestBody @Valid ShortestPathRequest request) {
        Long buildingId = buildingRepository.findOne(request.getBuildingId()).getId();
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
        ShortestPathResponse path = buildingService.findPath(buildingId, startNodeId, endNodeId);
        return ResponseEntity.ok().body(path);
    }
}
