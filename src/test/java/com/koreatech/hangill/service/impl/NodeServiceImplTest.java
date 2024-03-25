package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.dto.request.AddNodeToBuildingRequest;
import com.koreatech.hangill.dto.request.CreateBuildingRequest;
import com.koreatech.hangill.dto.request.CreateNodeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@Rollback(value = false)
class NodeServiceImplTest {
    @Autowired
    BuildingManagingServiceImpl buildingService;
    @Autowired
    NodeServiceImpl nodeService;
    @Test
    public void 건물에_노드_추가() throws Exception {
        //given
        Long buildingId = buildingService.saveBuilding(
                new CreateBuildingRequest("공학 2관", "컴공", null, null)
        );

        CreateNodeRequest createNodeRequest = new CreateNodeRequest(1, null, null, null, 1);
        CreateNodeRequest createNodeRequest2 = new CreateNodeRequest(2, null, null, null, 1);
        CreateNodeRequest createNodeRequest3 = new CreateNodeRequest(3, null, null, null, 1);


        //when
        nodeService.addNodeToBuilding(new AddNodeToBuildingRequest(buildingId, createNodeRequest));
        nodeService.addNodeToBuilding(new AddNodeToBuildingRequest(buildingId, createNodeRequest2));
        nodeService.addNodeToBuilding(new AddNodeToBuildingRequest(buildingId, createNodeRequest3));

        //then


    }
}