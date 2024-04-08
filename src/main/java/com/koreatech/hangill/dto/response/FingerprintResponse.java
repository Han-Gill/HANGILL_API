package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Fingerprint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FingerprintResponse {
    public FingerprintResponse(Fingerprint fingerprint) {
        this.ssid = fingerprint.getAccessPoint().getSsid();
        this.mac = fingerprint.getAccessPoint().getMac();
        this.rssi = fingerprint.getRssi();
    }
    @Schema(
            description = "AP의 ssid",
            nullable = true,
            example = "KOREATECH"
    )
    private String ssid;

    @Schema(
            description = "AP의 MAC 주소",
            nullable = true,
            example = "34:5D:AD:14:DB:20"
    )
    private String mac;

    @Schema(
            description = "신호 세기",
            nullable = true,
            example = "-47"
    )
    private Integer rssi;
}
