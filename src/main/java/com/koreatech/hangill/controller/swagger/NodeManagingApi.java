package com.koreatech.hangill.controller.swagger;

import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.request.BuildFingerprintRequest;
import com.koreatech.hangill.dto.request.BuildFingerprintWithBuildingNameRequest;
import com.koreatech.hangill.dto.request.NodePositionRequest;
import com.koreatech.hangill.dto.request.NodeSearchRequest;
import com.koreatech.hangill.dto.response.FingerprintsResponse;
import com.koreatech.hangill.dto.response.NodeDetailResponse;
import com.koreatech.hangill.dto.response.NodePositionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "노드 관리 API", description = "노드 관련 관리자 API")
public interface NodeManagingApi {

    @Operation(summary = "노드에 핑거 프린트 추가")
    @ApiResponses(
        value = {
                @ApiResponse(responseCode = "200", description = "핑거프린트 추가 성공"),
                @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @PostMapping("/api/v1/admin/node/fingerprint")
    ResponseEntity<Void> buildFingerprint(@RequestBody BuildFingerprintRequest request);



    @Operation(summary = "노드의 핑거프린트 목록 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"/* ,내용은 DTO의 schema를 따라 만들어줌.*/)
            }
    )
    @Parameter(name = "nodeId", description = "노드 id", example = "1")
    @GetMapping("/api/v1/admin/node/{nodeId}/fingerprint")
    ResponseEntity<FingerprintsResponse> fingerprints(@PathVariable Long nodeId);

    @Operation(summary = "건물이름을 통해 노드 핑거 프린트 추가")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "핑거프린트 추가 성공!")
    )
    @PostMapping("/api/v1/admin/node/fingerprint/building-name")
    ResponseEntity<Void> buildFingerprint(
            @RequestBody @Valid BuildFingerprintWithBuildingNameRequest request
    );

    @Operation(summary = "노드 세부 정보 조회")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "노드 조회 성공!")
    )
    @GetMapping("/api/v1/admin/node")
    ResponseEntity<NodeDetailResponse> findOne(@RequestBody @Valid NodeSearchRequest request);


    @Operation(summary = "사용자의 현재 위치 조회")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "현재 위치 조회 성공!")
    )
    @PostMapping("api/v1/admin/node/position")
    ResponseEntity<NodePositionResponse> findPosition(@RequestBody @Valid NodePositionRequest request);

}
