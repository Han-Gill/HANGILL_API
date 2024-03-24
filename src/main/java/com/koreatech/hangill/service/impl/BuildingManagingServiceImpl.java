package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.domain.Edge;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.*;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.service.BuildingManagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuildingManagingServiceImpl implements BuildingManagingService {
    private final BuildingRepository buildingRepository;
    private final NodeServiceImpl nodeService;

    @Override
    public Long saveBuilding(CreateBuildingRequest request) {
        Building building = new Building(request);
        validateDuplicatedBuilding(building.getName());
        buildingRepository.save(building);
        return building.getId();
    }

    public void validateDuplicatedBuilding(String name) {
        if (buildingRepository.findAll(name).size() > 0) throw new IllegalStateException("같은 이름의 건물이 있습니다.");
    }

    @Override
    public void addNode(AddNodeToBuildingRequest request) {
        Building building = buildingRepository.findOne(request.getBuildingId());
        nodeService.validateDuplicatedNode(
                new NodeSearch(
                        building.getId(),
                        request.getCreateNodeRequest().getNumber(),
                        request.getCreateNodeRequest().getFloor())
        );
        Node node = new Node(request.getCreateNodeRequest());
        building.addNode(node);
    }

    @Override
    public void addEdge(AddEdgeToBuildingRequest request) {
        Building building = buildingRepository.findOne(request.getBuildingId());
        // 먼저 연결할 Node 를 영속화해야함.
        Node startNode = nodeService.findOne(new NodeSearch(
                building.getId(),
                request.getStartNodeNumber(),
                request.getStartNodeFloor()
        ));

        Node endNode = nodeService.findOne(new NodeSearch(
                building.getId(),
                request.getEndNodeNumber(),
                request.getEndNodeFloor()
        ));
        building.addEdge(Edge.createEdge(startNode, endNode, request.getDistance()));
    }

    public void deleteEdge(DeleteEdgeRequest request) {

    }

    public void deleteNode(DeleteNodeRequest request) {

    }






}
