package com.koreatech.hangill.controller.swagger;

import com.koreatech.hangill.dto.request.AddEdgeToBuildingRequest;
import com.koreatech.hangill.dto.request.AddNodeToBuildingRequest;
import com.koreatech.hangill.dto.request.CreateBuildingRequest;
import com.koreatech.hangill.dto.request.UpdateBuildingRequest;
import com.koreatech.hangill.dto.response.BuildingsResponse;
import com.koreatech.hangill.dto.response.EdgesResponse;
import com.koreatech.hangill.dto.response.NodesResponse;
import com.koreatech.hangill.dto.response.NumberGraphResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "건물 관리 API", description = "건물 관련 관리자 API")
public interface BuildingManagingApi {
    @Operation(summary = "건물 삽입 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 삽입 성공")
    )
    @PostMapping("/api/v1/admin/building")
    ResponseEntity<Void> saveBuilding(@Valid @RequestBody CreateBuildingRequest request);

    @Operation(summary = "건물 목록 조회 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 목록 조회 성공")
    )
    @GetMapping("/api/v1/admin/buildings")
    ResponseEntity<BuildingsResponse> buildings();

    @Operation(summary = "건물 노드 삽입 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 노드 삽입 성공")
    )
    @PostMapping("/api/v1/admin/building/node")
    ResponseEntity<Void> saveNode(@Valid @RequestBody AddNodeToBuildingRequest request);

    @Operation(summary = "건물 간선 삽입 API", description = "반드시 양방향으로 간선 넣어주어야 하며 층끼리는 완전 그래프 구성")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 간선 삽입 성공")
    )
    @PostMapping("/api/v1/admin/building/edge")
    ResponseEntity<Void> saveEdge(@Valid @RequestBody AddEdgeToBuildingRequest request);

    @Operation(summary = "건물 그래프 조회 API", description = "방향 그래프(양방향)")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 그래프 조회 성공")
    )
    @GetMapping("/api/v1/admin/building/{buildingId}/graph")
    ResponseEntity<NumberGraphResponse> graph(@PathVariable Long buildingId);

    @Operation(summary = "건물 노드 목록 조회 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 노드 목록 조회 성공")
    )
    @GetMapping("/api/v1/admin/building/{buildingId}/nodes")
    ResponseEntity<NodesResponse> nodes(@PathVariable Long buildingId);

    @Operation(summary = "건물 간선 목록 조회 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 간선 목록 조회 성공")
    )
    @GetMapping("/api/v1/admin/building/{buildingId}/edges")
    ResponseEntity<EdgesResponse> edges(@PathVariable Long buildingId);

    @Operation(summary = "건물 노드 삭제 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 노드 삭제 성공")
    )
    @DeleteMapping("/api/v1/admin/building/node")
    ResponseEntity<Void> removeNode(@RequestHeader Long buildingId, @RequestHeader Long nodeId);

    @Operation(summary = "건물 간선 삭제 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 간선 삭제 성공")
    )
    @DeleteMapping("/api/v1/admin/building/edge")
    ResponseEntity<Void> removeEdge(@RequestHeader Long buildingId, @RequestHeader Long edgeId);

    @Operation(summary = "건물 삭제 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 삭제 성공")
    )
    @DeleteMapping("/api/v1/admin/building")
    ResponseEntity<Void> remove(@RequestHeader Long buildingId);

    @Operation(summary = "건물 내용 수정 API", description = "반드시 모든 필드를 입력!")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 수정 성공!")
    )
    @PutMapping("/api/v1/admin/building")
    ResponseEntity<Void> updateBuilding(@RequestBody @Valid UpdateBuildingRequest request);
}
