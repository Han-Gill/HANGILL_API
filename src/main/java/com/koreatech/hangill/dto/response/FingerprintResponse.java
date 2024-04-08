package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Fingerprint;
import lombok.Data;

@Data
public class FingerprintResponse {
    public FingerprintResponse(Fingerprint fingerprint) {
        this.ssid = fingerprint.getAccessPoint().getSsid();
        this.mac = fingerprint.getAccessPoint().getMac();
        this.rssi = fingerprint.getRssi();
    }
    private String ssid;
    private String mac;
    private Integer rssi;
}
