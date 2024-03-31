package com.koreatech.hangill.controller;

import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import com.koreatech.hangill.dto.NodeDto;
import com.koreatech.hangill.dto.request.ShortestPathRequest;
import com.koreatech.hangill.dto.response.ShortestPathResponse;
import com.koreatech.hangill.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping("/api/v1/building/nodes/{buildingId}/{nodeType}")
    public ResponseEntity<Map<String, Object>> findNodesByType(
            @PathVariable Long buildingId, @PathVariable NodeType nodeType) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Node> nodes = buildingService.findAllNodesByType(buildingId, nodeType);
            List<NodeDto> nodeDtos = nodes.stream()
                    .map(NodeDto::new)
                    .toList();
            resultMap.put("nodes", nodeDtos);
            resultMap.put("message", "SUCCESS!");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", "ERROR!");
            resultMap.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/building/path/{buildingId}/{startNodeId}/{endNodeId}")
    public ResponseEntity<Map<String, Object>> findPath(
            @PathVariable Long buildingId,
            @PathVariable Long startNodeId,
            @PathVariable Long endNodeId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            ShortestPathResponse path = buildingService.findPath(
                    new ShortestPathRequest(buildingId, startNodeId, endNodeId)
            );
            resultMap.put("path", path);
            resultMap.put("message", "SUCCESS!");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", "ERROR!");
            resultMap.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
