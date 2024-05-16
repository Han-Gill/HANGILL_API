package com.koreatech.hangill.controller.swagger;

import com.koreatech.hangill.domain.NodeType;
import com.koreatech.hangill.dto.request.ShortestPathRequest;
import com.koreatech.hangill.dto.response.NodeDtosResponse;
import com.koreatech.hangill.dto.response.ShortestPathResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "건물 관련 API", description = "FE에서 사용")
public interface BuildingApi {

    @Operation(summary = "건물 노드 목록 조회(타입별) API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 노드 목록 조회 성공")
    )
    @GetMapping("/api/v1/building/nodes/{buildingId}/{nodeType}")
    ResponseEntity<NodeDtosResponse> findNodesByType(
            @PathVariable Long buildingId, @PathVariable NodeType nodeType);


    @Operation(summary = "건물에서 경로 조회 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물에서 경로 조회 성공")
    )
    @PostMapping("/api/v1/building/path")
    ResponseEntity<ShortestPathResponse> findPath(ShortestPathRequest request);
}
