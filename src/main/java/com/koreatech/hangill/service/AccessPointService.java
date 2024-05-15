package com.koreatech.hangill.service;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.dto.request.AccessPointRequest;
import com.koreatech.hangill.dto.request.BuildFingerprintRequest;

public interface AccessPointService {
    Long save(AccessPointRequest request);
    void saveAllBySignals(BuildFingerprintRequest request);
}
