package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Building;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuildingResponse {
    public BuildingResponse(Building building) {
        this.id = building.getId();
        this.name = building.getName();
        this.description = building.getDescription();
        this.latitude = building.getLatitude();
        this.longitude = building.getLongitude();
    }
    @Schema(
            description = "건물 ID",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "건물의 이름",
            example = "공학 2관"
    )
    private String name;

    @Schema(
            description = "건물의 세부 설명",
            example = "컴퓨터 공학부 학생들과 건축, 디자인 공학부 학생들이 수업 듣는 공학관"
    )
    private String description;

    @Schema(
            description = "건물의 위도",
            example = "37.23212"
    )
    private BigDecimal latitude;

    @Schema(
            description = "건물의 경도",
            example = "127.23134"
    )
    private BigDecimal longitude;
}
