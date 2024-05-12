package com.koreatech.hangill.controller.swagger;

import com.koreatech.hangill.dto.request.AccessPointRequest;
import com.koreatech.hangill.dto.response.AccessPointsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = " 공유기 관리 API", description = "건물에 AP를 추가, 조회하는 API")
public interface AccessPointManagingApi {
    @Operation(summary = "건물에 공유기 저장 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물에 공유기 삽입 성공")
    )
    @PostMapping("/api/v1/admin/access-point")
    ResponseEntity<Void> save(@RequestBody @Valid AccessPointRequest request);

    @Operation(summary = "건물에 속한 공유기 목록 조회 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "건물 공유기 목록 조회 성제")
    )
    @GetMapping("/api/v1/admin/{buildingId}/access-points")
    ResponseEntity<AccessPointsResponse> accessPoints(@PathVariable Long buildingId);
}
