package com.koreatech.hangill.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccessPointRequest {
    @NotNull(message = "건물ID 필수로 입력!")
    private Long buildingId;
    private String ssid;
    @NotEmpty(message = "mac 주소 필수로 입력!")
    private String mac;
}
