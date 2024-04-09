package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.AccessPoint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AccessPointResponse {
    public AccessPointResponse(AccessPoint accessPoint) {
        this.ssid = accessPoint.getSsid();
        this.mac = accessPoint.getMac();
    }

    @Schema(
            description = "AP의 ssid",
            example = "KOREATECH"
    )
    private String ssid;
    @Schema(
            description = "AP의 mac주소",
            nullable = true,
            example = "4D:42:56:1A:2B:4C"
    )
    private String mac;
}
