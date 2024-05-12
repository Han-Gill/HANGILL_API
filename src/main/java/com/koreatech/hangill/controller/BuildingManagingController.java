package com.koreatech.hangill.controller;

import com.koreatech.hangill.controller.swagger.BuildingManagingApi;
import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.domain.Edge;
import com.koreatech.hangill.dto.request.AddEdgeToBuildingRequest;
import com.koreatech.hangill.dto.request.AddNodeToBuildingRequest;
import com.koreatech.hangill.dto.request.CreateBuildingRequest;
import com.koreatech.hangill.dto.request.UpdateBuildingRequest;
import com.koreatech.hangill.dto.response.*;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.repository.EdgeRepository;
import com.koreatech.hangill.service.BuildingManagingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BuildingManagingController implements BuildingManagingApi {
    private final BuildingManagingService buildingManagingService;
    private final BuildingRepository buildingRepository;
    private final EdgeRepository edgeRepository;

    @PostMapping("/api/v1/admin/building")
    public ResponseEntity<Void> saveBuilding(@Valid @RequestBody CreateBuildingRequest request) {
        buildingManagingService.saveBuilding(request);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/api/v1/admin/buildings")
    public ResponseEntity<BuildingsResponse> buildings() {
        List<BuildingResponse> buildings = buildingRepository.findAll().stream()
                .map(BuildingResponse::new)
                .toList();

        return ResponseEntity.ok().body(new BuildingsResponse(buildings));

    }

    @PostMapping("/api/v1/admin/building/node")
    public ResponseEntity<Void> saveNode(@Valid @RequestBody AddNodeToBuildingRequest request) {
        buildingManagingService.addNode(request);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/api/v1/admin/building/edge")
    public ResponseEntity<Void> saveEdge(@Valid @RequestBody AddEdgeToBuildingRequest request) {
        buildingManagingService.addEdge(request);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/api/v1/admin/building/{buildingId}/graph")
    public ResponseEntity<NumberGraphResponse> graph(@PathVariable Long buildingId) {
        Map<Long, List<Long[]>> numberGraph = buildingManagingService.findNumberGraph(buildingId);
        return ResponseEntity.ok().body(new NumberGraphResponse(numberGraph));

    }

    @GetMapping("/api/v1/admin/building/{buildingId}/nodes")
    public ResponseEntity<NodesResponse> nodes(@PathVariable Long buildingId) {
        Building building = buildingRepository.findOne(buildingId);
        List<NodeResponse> nodes = building.getNodes().stream()
                .map(NodeResponse::new)
                .toList();
        return ResponseEntity.ok().body(new NodesResponse(nodes));

    }

    @GetMapping("/api/v1/admin/building/{buildingId}/edges")
    public ResponseEntity<EdgesResponse> edges(@PathVariable Long buildingId) {
        List<Edge> edges = edgeRepository.findAll(buildingId);
        List<EdgeResponse> edgeResponses = edges.stream()
                .map(EdgeResponse::new)
                .toList();
        return ResponseEntity.ok().body(new EdgesResponse(edgeResponses));

    }

    @DeleteMapping("/api/v1/admin/building/node")
    public ResponseEntity<Void> removeNode(@RequestHeader Long buildingId, @RequestHeader Long nodeId) {
        log.info("buildingId : {}", buildingId);
        log.info("nodeId : {}", nodeId);
        buildingManagingService.deleteNode(buildingId, nodeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/admin/building/edge")
    public ResponseEntity<Void> removeEdge(@RequestHeader Long buildingId, @RequestHeader Long edgeId) {
        log.info("buildingId : {}", buildingId);
        log.info("edgeId : {}", edgeId);
        buildingManagingService.deleteEdge(buildingId, edgeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/admin/building")
    public ResponseEntity<Void> remove(@RequestHeader Long buildingId) {
        buildingManagingService.deleteBuilding(buildingId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/admin/building")
    public ResponseEntity<Void> updateBuilding(@RequestBody @Valid UpdateBuildingRequest request) {
        buildingManagingService.updateBuilding(request);
        return ResponseEntity.ok().build();
    }

}
