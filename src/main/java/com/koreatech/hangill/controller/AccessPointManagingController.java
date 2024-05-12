package com.koreatech.hangill.controller;

import com.koreatech.hangill.controller.swagger.AccessPointManagingApi;
import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.dto.request.AccessPointRequest;
import com.koreatech.hangill.dto.response.AccessPointResponse;
import com.koreatech.hangill.dto.response.AccessPointsResponse;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.service.AccessPointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccessPointManagingController implements AccessPointManagingApi {
    private final AccessPointService accessPointService;
    private final AccessPointRepository accessPointRepository;

    private final BuildingRepository buildingRepository;

    @PostMapping("/api/v1/admin/access-point")
    public ResponseEntity<Void> save(@RequestBody @Valid AccessPointRequest request) {
        accessPointService.save(request);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/api/v1/admin/{buildingId}/access-points")
    public ResponseEntity<AccessPointsResponse> accessPoints(@PathVariable Long buildingId) {
        Building building = buildingRepository.findOne(buildingId);
        List<AccessPointResponse> accessPoints = accessPointRepository.findAll(building.getId()).stream()
                .map(AccessPointResponse::new)
                .toList();
        return ResponseEntity.ok().body(new AccessPointsResponse(accessPoints));

    }

}
