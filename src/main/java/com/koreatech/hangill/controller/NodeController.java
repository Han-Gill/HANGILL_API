package com.koreatech.hangill.controller;

import com.koreatech.hangill.controller.swagger.NodeApi;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.response.NodeDetailResponse;
import com.koreatech.hangill.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NodeController implements NodeApi {
    private final NodeRepository nodeRepository;
    @GetMapping("/api/v1/admin/node/{nodeId}")
    public ResponseEntity<NodeDetailResponse> findOne(@PathVariable Long nodeId) {
        Node node = nodeRepository.findOne(nodeId);
        return ResponseEntity.ok(new NodeDetailResponse(node));
    }


}
