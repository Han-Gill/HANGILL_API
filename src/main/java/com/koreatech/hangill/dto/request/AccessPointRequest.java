package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccessPointRequest {
    @NotNull(message = "건물ID 필수로 입력!")
    @Schema(
            description = "AP를 추가할 건물 ID",
            nullable = false,
            example = "1"
    )
    private Long buildingId;

    @Schema(
            description = "추가할 AP의 SSID",
            nullable = false,
            example = "KOREATECH"
    )
    private String ssid;

    @Schema(
            description = "추가할 AP의 MAC 주소",
            nullable = false,
            example = "4D:5C:22:F2:FF:2A"
    )
    @NotEmpty(message = "mac 주소 필수로 입력!")
    private String mac;
}
