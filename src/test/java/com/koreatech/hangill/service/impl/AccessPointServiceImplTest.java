package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.dto.request.AccessPointRequest;
import com.koreatech.hangill.dto.request.CreateBuildingRequest;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.service.BuildingManagingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccessPointServiceImplTest {
    @Autowired
    AccessPointServiceImpl accessPointService;

    @Autowired
    AccessPointRepository accessPointRepository;

    @Autowired
    BuildingManagingService buildingManagingService;

    @Test
    public void AP_저장_테스트() throws Exception {
        //given
        CreateBuildingRequest request = new CreateBuildingRequest(
                "2공학관", null, null, null
        );
        Long buildingId = buildingManagingService.saveBuilding(request);

        AccessPointRequest accessPointRequest1 = createAPRequest("A", "A", buildingId);
        AccessPointRequest accessPointRequest2 = createAPRequest("B", "B", buildingId);
        AccessPointRequest accessPointRequest3 = createAPRequest("C", "C", buildingId);

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

    private static AccessPointRequest createAPRequest(String ssid, String mac, long buildingId) {
        AccessPointRequest accessPointRequest = new AccessPointRequest();
        accessPointRequest.setSsid(ssid);
        accessPointRequest.setMac(mac);
        accessPointRequest.setBuildingId(buildingId);
        return accessPointRequest;
    }
}