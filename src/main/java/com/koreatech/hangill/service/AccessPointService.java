package com.koreatech.hangill.service;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.dto.request.AccessPointRequest;

public interface AccessPointService {
    Long save(AccessPointRequest request);
}
