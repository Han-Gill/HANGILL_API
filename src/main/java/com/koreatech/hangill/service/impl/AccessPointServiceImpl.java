package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.dto.request.AccessPointRequest;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.service.AccessPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccessPointServiceImpl implements AccessPointService {
    private final AccessPointRepository accessPointRepository;
    private final BuildingRepository buildingRepository;

    @Transactional
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


}
