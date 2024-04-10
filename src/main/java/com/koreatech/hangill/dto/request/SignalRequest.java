package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignalRequest {
    @Schema(
            description = "AP의 SSID",
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
            description = "rssi값(AP의 신호세기)",
            nullable = true,
            example = "-46"
    )
    @NotNull(message = "반드시 rssi 값을 입력해야함.")
    private Integer rssi;
}
