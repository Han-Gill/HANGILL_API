package com.koreatech.hangill.controller;

import com.koreatech.hangill.controller.swagger.NodeApi;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeDto;
import com.koreatech.hangill.dto.request.FindNodeForNameRequest;
import com.koreatech.hangill.dto.response.NodeDetailResponse;
import com.koreatech.hangill.dto.response.NodeDtosResponse;
import com.koreatech.hangill.repository.NodeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NodeController implements NodeApi {
    private final NodeRepository nodeRepository;

    @GetMapping("/api/v1/admin/node/{nodeId}")
    public ResponseEntity<NodeDetailResponse> findOne(@PathVariable Long nodeId) {
        Node node = nodeRepository.findOne(nodeId);
        return ResponseEntity.ok(new NodeDetailResponse(node));
    }


    @PostMapping("/api/v1/nodes")
    public ResponseEntity<NodeDtosResponse> findAll(@RequestBody @Valid FindNodeForNameRequest request) {
        List<NodeDto> findNodes = nodeRepository.findAll(request.getBuildingId(), request.getNodeName())
                .stream()
                .map(NodeDto::new)
                .toList();
        return ResponseEntity.ok(new NodeDtosResponse(findNodes));
    }


}
