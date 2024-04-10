package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AccessPointRequest {
    @NotBlank(message = "건물이름은 반드시 입력해주세요")
    @Schema(
            description = "AP를 추가할 건물 이름",
            nullable = false,
            example = "공학 2관"
    )
    private String buildingName;

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
    @NotEmpty(message = "mac 주소 필수로 입력해주세요")
    private String mac;
}
