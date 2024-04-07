package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.repository.AccessPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccessPointServiceImpl {
    private final AccessPointRepository accessPointRepository;

    public Long save(AccessPoint accessPoint) {
        accessPointRepository.save(accessPoint);
        return accessPoint.getId();
    }

}
