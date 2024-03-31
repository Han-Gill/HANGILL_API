package com.koreatech.hangill.service;

import com.koreatech.hangill.dto.request.AddEdgeToBuildingRequest;
import com.koreatech.hangill.dto.request.AddNodeToBuildingRequest;
import com.koreatech.hangill.dto.request.CreateBuildingRequest;

import java.util.List;
import java.util.Map;

public interface BuildingManagingService {
    Long saveBuilding(CreateBuildingRequest request);
    void addNode(AddNodeToBuildingRequest request);
    void addEdge(AddEdgeToBuildingRequest request);
    Map<Long, List<Long[]>> findNumberGraph(Long id);

    void deleteEdge(Long buildingId, Long edgeId);
    void deleteNode(Long buildingId, Long nodeId);

    void deleteBuilding(Long buildingId);
}
