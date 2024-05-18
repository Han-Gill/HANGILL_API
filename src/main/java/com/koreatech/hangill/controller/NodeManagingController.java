package com.koreatech.hangill.controller;

import com.koreatech.hangill.controller.swagger.NodeManagingApi;
import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.domain.Fingerprint;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.BuildFingerprintRequest;
import com.koreatech.hangill.dto.request.BuildFingerprintWithBuildingNameRequest;
import com.koreatech.hangill.dto.request.NodePositionRequest;
import com.koreatech.hangill.dto.request.NodeSearchRequest;
import com.koreatech.hangill.dto.response.FingerprintResponse;
import com.koreatech.hangill.dto.response.FingerprintsResponse;
import com.koreatech.hangill.dto.response.NodeDetailResponse;
import com.koreatech.hangill.dto.response.NodePositionResponse;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.service.FindPositionService;
import com.koreatech.hangill.service.NodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class NodeManagingController implements NodeManagingApi {
    private final NodeService nodeService;
    private final BuildingRepository buildingRepository;
    private final FindPositionService findPositionService;

    @PostMapping("/api/v1/admin/node/fingerprint")
    public ResponseEntity<Void> buildFingerprint(@RequestBody @Valid BuildFingerprintRequest request) {
        log.info(request.toString());
        nodeService.buildFingerPrint(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/admin/node/{nodeId}/fingerprint")
    public ResponseEntity<FingerprintsResponse> fingerprints(@PathVariable Long nodeId) {
        List<Fingerprint> fingerprints = nodeService.fingerprints(nodeId);
        List<FingerprintResponse> fingerprintResponses = fingerprints.stream()
                .map(FingerprintResponse::new)
                .toList();
        return ResponseEntity.ok().body(new FingerprintsResponse(fingerprintResponses));
    }


    @PostMapping("/api/v1/admin/node/fingerprint/building-name")
    public ResponseEntity<Void> buildFingerprint(
            @RequestBody @Valid BuildFingerprintWithBuildingNameRequest request
    ) {
        Building building = buildingRepository.findOne(request.getNode().getBuildingName());
        Node node = nodeService.findOne(
                new NodeSearch(
                        building.getId(), request.getNode().getNumber(), request.getNode().getFloor()
                )
        );
        buildFingerprint(new BuildFingerprintRequest(node.getId(), request.getSignals()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/admin/node")
    public ResponseEntity<NodeDetailResponse> findOne(@RequestBody @Valid NodeSearchRequest request) {
        Building building = buildingRepository.findOne(request.getBuildingName());
        Node node = nodeService.findOne(new NodeSearch(building.getId(), request.getNumber(), request.getFloor()));
        return ResponseEntity.ok(new NodeDetailResponse(node));
    }

    @PostMapping("/api/v1/admin/node/position")
    public ResponseEntity<NodePositionResponse> findPosition(@RequestBody @Valid NodePositionRequest request) {
        Node findNode = findPositionService.findPosition(request);
        log.info("responseNode : {}", findNode.getNumber() + "번호 " + findNode.getFloor() + "층");
        return ResponseEntity.ok().body(new NodePositionResponse(findNode));
    }
}
