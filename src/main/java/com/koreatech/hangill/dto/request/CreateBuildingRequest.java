package com.koreatech.hangill.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateBuildingRequest {
    @NotEmpty(message = "건물 이름을 반드시 입력해주세요")
    private String name;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
