package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.AccessPoint;
import lombok.Data;

@Data
public class AccessPointResponse {
    public AccessPointResponse(AccessPoint accessPoint) {
        this.ssid = accessPoint.getSsid();
        this.mac = accessPoint.getMac();
    }

    private String ssid;
    private String mac;
}
