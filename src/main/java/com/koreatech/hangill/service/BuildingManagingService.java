package com.koreatech.hangill.service;

import com.koreatech.hangill.dto.request.AddEdgeToBuildingRequest;
import com.koreatech.hangill.dto.request.AddNodeToBuildingRequest;
import com.koreatech.hangill.dto.request.CreateBuildingRequest;

public interface BuildingManagingService {
    Long saveBuilding(CreateBuildingRequest request);
    void addNode(AddNodeToBuildingRequest request);
    void addEdge(AddEdgeToBuildingRequest request);


}
