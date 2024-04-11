package com.koreatech.hangill.controller.swagger;

import com.koreatech.hangill.dto.response.NodeDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "노드 관련 API", description = "FE에서 사용")
public interface NodeApi {
    @Operation(summary = "노드 세부 정보 조회")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "노드 조회 성공!")
    )
    @GetMapping("/api/v1/admin/node/{nodeId}")
    ResponseEntity<NodeDetailResponse> findOne(@PathVariable Long nodeId);
}
