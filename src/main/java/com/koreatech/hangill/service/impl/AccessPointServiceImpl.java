package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.dto.request.AccessPointRequest;
import com.koreatech.hangill.exception.AccessPointNotFoundException;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.service.AccessPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccessPointServiceImpl implements AccessPointService {
    private final AccessPointRepository accessPointRepository;
    private final BuildingRepository buildingRepository;

    public Long save(AccessPointRequest request) {
        validateDuplicatedMac(request.getMac());
        Building building = buildingRepository.findOne(request.getBuildingName());
        AccessPoint accessPoint = new AccessPoint(
                request.getSsid(),
                request.getMac(),
                building
        );
        accessPointRepository.save(accessPoint);
        return accessPoint.getId();
    }

    private void validateDuplicatedMac(String mac) {
        if (accessPointRepository.findAll(mac).size() > 0) throw new IllegalArgumentException("동일 MAC 주소를 가진 AP가 존재!");
    }

    public void turnOffBySsid(String ssid, Long buildingId) {
        List<AccessPoint> accessPoints = accessPointRepository.findAll(ssid, buildingId);
        for (AccessPoint accessPoint : accessPoints){
            accessPoint.turnOff();
        }
    }
    public void turnOnBySsid(String ssid, Long buildingId) {
        List<AccessPoint> accessPoints = accessPointRepository.findAll(ssid, buildingId);
        for (AccessPoint accessPoint : accessPoints){
            accessPoint.turnOn();
        }
    }

    public void turnOffByMac(String mac) {
        List<AccessPoint> accessPoints = accessPointRepository.findAll(mac);
        if (accessPoints.size() == 0) throw AccessPointNotFoundException.withDetail(mac + " mac주소를 가진");
        accessPoints.get(0).turnOff();
    }
    public void turnOnByMac(String mac) {
        List<AccessPoint> accessPoints = accessPointRepository.findAll(mac);
        if (accessPoints.size() == 0) throw AccessPointNotFoundException.withDetail(mac + " mac주소를 가진");
        accessPoints.get(0).turnOn();
    }

}
