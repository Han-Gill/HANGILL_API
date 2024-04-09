package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.*;
import com.koreatech.hangill.dto.request.*;
import com.koreatech.hangill.dto.response.FingerprintResponse;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.repository.BuildingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class NodeServiceImplTest {
    @Autowired
    BuildingManagingServiceImpl buildingService;
    @Autowired
    NodeServiceImpl nodeService;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    AccessPointRepository accessPointRepository;
    @Autowired
    AccessPointServiceImpl accessPointService;

//    @Rollback(value = false)
    @Test
    public void 핑거_프린트_구성() throws Exception {
        //given
        Long buildingId = buildingService.saveBuilding(new CreateBuildingRequest(
                "공학 2관", "컴공 & 건디", null, null
        ));
        Building building = buildingRepository.findOne(buildingId);
        // 2공에서 A, B, C 세가지 AP를 이용해 위치를 특정함
        AccessPoint accessPointA = new AccessPoint("A", "A", building);
        AccessPoint accessPointB = new AccessPoint("B", "B", building);
        AccessPoint accessPointC = new AccessPoint("C", "C", building);
        accessPointRepository.save(accessPointA);
        accessPointRepository.save(accessPointB);
        accessPointRepository.save(accessPointC);


        buildingService.addNode(new AddNodeToBuildingRequest(
                buildingId,
                new CreateNodeRequest(1, "1", "1", NodeType.ROAD, 1)
        ));
        buildingService.addNode(new AddNodeToBuildingRequest(
                buildingId,
                new CreateNodeRequest(2, "2", "2", NodeType.ROAD, 1)
        ));
        buildingService.addNode(new AddNodeToBuildingRequest(
                buildingId,
                new CreateNodeRequest(3, "3", "3", NodeType.ROAD, 1)
        ));

        Node node1 = building.getNodes().get(0);


        BuildFingerprintRequest request1 = new BuildFingerprintRequest();
        request1.setNodeId(node1.getId());
        List<SignalRequest> signalRequests = new ArrayList<>();
        // Node1에서 세 가지 신호가 잡힘
        // A, B는 위치 특정 시 사용하는 신호
        // D는 사용자 핫스팟과 같은 위치 특정시 사용하지 않는 신호
        signalRequests.add(new SignalRequest("A", "A", -43)); // 건물에서 사용하는 AP 신호
        signalRequests.add(new SignalRequest("B", "B", -77)); // 건물에서 사용하는 AP 신호
        signalRequests.add(new SignalRequest("D", "D", -87)); // 건물에서 사용하지 않는 AP 신호
        request1.setSignals(signalRequests);



        //when
//        accessPointA.turnOff();
        nodeService.buildFingerPrint(request1);

        //then

        List<Fingerprint> fingerprints = node1.getFingerprints();
        List<FingerprintResponse> fingerprintResponses = nodeService.fingerprints(node1.getId());
        int rssiSum = 0;
        for (int i = 0; i < fingerprints.size(); i++) {
            Fingerprint fp = fingerprints.get(i);
            FingerprintResponse fpr = fingerprintResponses.get(i);
            assertEquals(fp.getRssi(), fpr.getRssi());
            assertEquals(fp.getAccessPoint().getSsid(), fpr.getSsid());
            assertEquals(fp.getAccessPoint().getMac(), fpr.getMac());
            rssiSum += fp.getRssi();
        }

        assertEquals(-120, rssiSum, "신호세기 합이 -120이어야함!!");




    }


}