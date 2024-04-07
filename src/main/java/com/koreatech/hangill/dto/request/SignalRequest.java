package com.koreatech.hangill.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignalRequest {
    private String ssid;
    private String mac;
    private Integer rssi;
}
