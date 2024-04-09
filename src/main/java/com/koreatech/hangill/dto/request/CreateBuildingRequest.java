package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBuildingRequest {
    @NotEmpty(message = "건물 이름을 반드시 입력해주세요")
    @Schema(
            description = "추가할 건물의 이름",
            nullable = false,
            example = "2공학관"
    )
    private String name;

    @Schema(
            description = "추가할 건물의 설명",
            nullable = false,
            example = "컴퓨터 공학부와 건축, 디자인 학생들이 전공 수업을 듣는 곳"
    )
    private String description;
    @Schema(
            description = "추가할 건물의 위도",
            nullable = true,
            example = "37.4123"
    )
    private BigDecimal latitude;
    @Schema(
            description = "추가할 건물의 경도",
            nullable = true,
            example = "127.5341"
    )
    private BigDecimal longitude;
}
