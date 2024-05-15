package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.domain.Fingerprint;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.*;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.service.BuildingManagingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccessPointServiceImplTest {
    @Autowired
    AccessPointServiceImpl accessPointService;

    @Autowired
    AccessPointRepository accessPointRepository;

    @Autowired
    BuildingManagingService buildingManagingService;

    @Autowired
    NodeServiceImpl nodeService;


    @Test
    public void 신호들로부터_AP와_핑거프린트_모두_저장() throws Exception{
        //given
        CreateBuildingRequest request = new CreateBuildingRequest(
                "2공학관", null, null, null
        );
        Long buildingId = buildingManagingService.saveBuilding(request);

        buildingManagingService.addNode(new AddNodeToBuildingRequest(
                buildingId, new CreateNodeRequest(1, null, null, NodeType.STAIR, 1)
        ));

        Node node = nodeService.findOne(new NodeSearch(buildingId, 1, 1));


        List<SignalRequest> signals = new ArrayList<>();
        signals.add(new SignalRequest("A", "A", -99));
        signals.add(new SignalRequest("B", "B", -87));
        signals.add(new SignalRequest("C", "C", -32));


        //when
        // 2공학관 1번 노드에 위 세가지 신호가 잡힘.
        nodeService.buildAccessPointAndFingerPrint(new BuildFingerprintRequest(node.getId(), signals));
        //then

        // AP가 먼저 저장되어야 함.
        AccessPoint accessPoint1 = accessPointRepository.findAll("A").get(0);
        AccessPoint accessPoint2 = accessPointRepository.findAll("B").get(0);
        AccessPoint accessPoint3 = accessPointRepository.findAll("C").get(0);
        assertEquals("A", accessPoint1.getSsid());
        assertEquals("B", accessPoint2.getSsid());
        assertEquals("C", accessPoint3.getSsid());

        // Fingerprint도 저장되어야 함.
        List<Fingerprint> fingerprints = node.getFingerprints();
        assertEquals(3, fingerprints.size());

    }
    @Test
    public void AP_저장_테스트() throws Exception {
        //given
        CreateBuildingRequest request = new CreateBuildingRequest(
                "2공학관", null, null, null
        );
        Long buildingId = buildingManagingService.saveBuilding(request);

        AccessPointRequest accessPointRequest1 = createAPRequest("A", "A", request.getName());
        AccessPointRequest accessPointRequest2 = createAPRequest("B", "B", request.getName());
        AccessPointRequest accessPointRequest3 = createAPRequest("C", "C", request.getName());
        //when
        Long save1 = accessPointService.save(accessPointRequest1);
        Long save2 = accessPointService.save(accessPointRequest2);
        Long save3 = accessPointService.save(accessPointRequest3);

        //then
        AccessPoint ap1 = accessPointRepository.findOne(save1);
        assertEquals(accessPointRequest1.getSsid(), ap1.getSsid(), "ssid 확인!");
        assertEquals(accessPointRequest1.getMac(), ap1.getMac(), "MAC 확인!");
        AccessPoint ap2 = accessPointRepository.findOne(save2);
        assertEquals(accessPointRequest2.getSsid(), ap2.getSsid(), "ssid 확인!");
        assertEquals(accessPointRequest2.getMac(), ap2.getMac(), "MAC 확인!");
        AccessPoint ap3 = accessPointRepository.findOne(save3);
        assertEquals(accessPointRequest3.getSsid(), ap3.getSsid(), "ssid 확인!");
        assertEquals(accessPointRequest3.getMac(), ap3.getMac(), "MAC 확인!");

        assertEquals(3, accessPointRepository.findAll(buildingId).size(), "공학 2관엔 3개의 AP가 있어야함");
    }

    private static AccessPointRequest createAPRequest(String ssid, String mac, String buildingName) {
        AccessPointRequest accessPointRequest = new AccessPointRequest();
        accessPointRequest.setSsid(ssid);
        accessPointRequest.setMac(mac);
        accessPointRequest.setBuildingName(buildingName);
        return accessPointRequest;
    }
}