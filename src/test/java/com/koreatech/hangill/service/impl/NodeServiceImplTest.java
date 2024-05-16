package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.*;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.*;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.repository.NodeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @Autowired
    NodeRepository nodeRepository;

    @PersistenceContext
    EntityManager em;

    //    @Rollback(value = false)
    @Test
    public void 핑거_프린트_구성_및_재구성() throws Exception {
        //given
        Long buildingId = buildingService.saveBuilding(new CreateBuildingRequest(
                "공학 2관", "컴공 & 건디", null, null
        ));
        Building building = buildingRepository.findOne(buildingId);
        // 2공에서 A, B, C 세가지 AP를 이용해 위치를 특정함
        AccessPoint accessPointA = new AccessPoint("A", "A", building);
        AccessPoint accessPointB = new AccessPoint("B", "B", building);
        AccessPoint accessPointC = new AccessPoint("C", "C", building);
        AccessPoint accessPointD = new AccessPoint("D", "D", building);
        AccessPoint accessPointE = new AccessPoint("E", "E", building);
        accessPointRepository.save(accessPointA);
        accessPointRepository.save(accessPointB);
        accessPointRepository.save(accessPointC);
        accessPointRepository.save(accessPointD);
        accessPointRepository.save(accessPointE);


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


        BuildFingerprintRequest request1 = getBuildFingerprintRequest(node1, -43, -77, -87);


        //when
        accessPointService.turnOffBySsid(accessPointD.getSsid(), building.getId());
        nodeService.buildFingerPrint(request1);

        //then

        List<Fingerprint> fingerprints = nodeService.fingerprints(node1.getId());
        int rssiSum = 0;
        for (Fingerprint fp : fingerprints) {
            rssiSum += fp.getRssi();
        }

        assertEquals(-207, rssiSum, "신호세기 합이 -207!!");


        BuildFingerprintRequest request2 = getBuildFingerprintRequest(node1, -10, -50, -40);
        nodeService.buildFingerPrint(request2);

        fingerprints = nodeService.fingerprints(node1.getId());
        rssiSum = 0;
        for (Fingerprint fp : fingerprints) {
            rssiSum += fp.getRssi();
        }

        assertEquals(-100, rssiSum, "신호세기 합이 -100이어야함!!");

    }

    private static BuildFingerprintRequest getBuildFingerprintRequest(Node node1, int rssi1, int rssi2, int rssi3) {
        BuildFingerprintRequest request1 = new BuildFingerprintRequest();
        request1.setNodeId(node1.getId());
        List<SignalRequest> signalRequests = new ArrayList<>();
        // Node1에서 세 가지 신호가 잡힘
        // A, B는 위치 특정 시 사용하는 신호
        // D는 사용자 핫스팟과 같은 위치 특정시 사용하지 않는 신호
        signalRequests.add(new SignalRequest("A", "A", rssi1)); // 건물에서 사용하는 AP 신호
        signalRequests.add(new SignalRequest("B", "B", rssi2)); // 건물에서 사용하는 AP 신호
        signalRequests.add(new SignalRequest("D", "D", rssi3)); // 건물에서 사용하지 않는 AP 신호
        request1.setSignals(signalRequests);
        return request1;
    }

    @Test
    public void 위치_확인_테스트() throws Exception {
        //given
        Long buildingId = buildingService.saveBuilding(new CreateBuildingRequest(
                "공학 2관", "컴공 & 건디", null, null
        ));
        Building building = buildingRepository.findOne(buildingId);
        // 5개의 ACCESS POINT
        accessPointService.save(new AccessPointRequest(building.getName(), "A", "A"));
        accessPointService.save(new AccessPointRequest(building.getName(), "B", "B"));
        accessPointService.save(new AccessPointRequest(building.getName(), "C", "C"));
        accessPointService.save(new AccessPointRequest(building.getName(), "D", "D"));
        accessPointService.save(new AccessPointRequest(building.getName(), "E", "E"));

        // 3개의 노드
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
        Node node1 = nodeService.findOne(new NodeSearch(buildingId, 1, 1));
        Node node2 = nodeService.findOne(new NodeSearch(buildingId, 2, 1));
        Node node3 = nodeService.findOne(new NodeSearch(buildingId, 3, 1));


        // 노드 1의 핑거프린트
        List<SignalRequest> node1Signals = new ArrayList<>();
        node1Signals.add(new SignalRequest("A", "A", -88));
        node1Signals.add(new SignalRequest("B", "B", -33));
        node1Signals.add(new SignalRequest("C", "C", -54));
        nodeService.buildFingerPrint(new BuildFingerprintRequest(node1.getId(), node1Signals));

        // 노드 2의 핑거 프린트
        List<SignalRequest> node2Signals = new ArrayList<>();
        node2Signals.add(new SignalRequest("A", "A", -104));
        node2Signals.add(new SignalRequest("B", "B", -53));
        node2Signals.add(new SignalRequest("C", "C", -32));
        node2Signals.add(new SignalRequest("D", "D", -99));
        nodeService.buildFingerPrint(new BuildFingerprintRequest(node2.getId(), node2Signals));

        // 노드 3의 핑거프린트
        List<SignalRequest> node3Signals = new ArrayList<>();
        node3Signals.add(new SignalRequest("B", "B", -88));
        node3Signals.add(new SignalRequest("C", "C", -54));
        node3Signals.add(new SignalRequest("D", "D", -52));
        nodeService.buildFingerPrint(new BuildFingerprintRequest(node3.getId(), node3Signals));


        accessPointService.turnOffBySsid("D", buildingId);

        em.flush();
        em.clear();

        node3 = nodeRepository.findOne(node3.getId());

        //when
        List<SignalRequest> userSignals = new ArrayList<>();
        userSignals.add(new SignalRequest("B", "B", -88));
        userSignals.add(new SignalRequest("C", "C", -54));
        userSignals.add(new SignalRequest("D", "D", -52));
        userSignals.add(new SignalRequest("E", "E", -52));
        userSignals.add(new SignalRequest("F", "F", -52));
        userSignals.add(new SignalRequest("G", "G", -52));
        Node searchNode = nodeService.findPosition(new NodePositionRequest(buildingId, userSignals));
        //then
        assertEquals(node3, searchNode);
    }



}