package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateBuildingRequest {
    @NotNull(message = "수정할 건물ID는 반드시 입력해주세요.")
    @Schema(description = "수정할 건물 ID", nullable = false, example = "1")
    private Long buildingId;

    @NotBlank(message = "수정할 건물이름은 반드시 입력해주세요.")
    @Schema(description = "수정할 건물 이름", nullable = false, example = "공학 2관")
    private String buildingName;

    @NotBlank(message = "수정할 건물설명은 반드시 입력해주세요.")
    @Schema(description = "수정할 건물 설명", nullable = false, example = "컴퓨터 공학부 학생들과 건축, 디자인 공학부 학생들이 수업 듣는 공학관")
    private String description;

    @NotNull(message = "수정할 건물 위도는 반드시 입력해주세요.")
    @Schema(description = "수정할 건물 위도", nullable = false, example = "37.23212")
    private BigDecimal latitude;

    @NotNull(message = "수정할 건물 경도는 반드시 입력해주세요.")
    @Schema(description = "수정할 건물 경도", nullable = false, example = "127.23134")
    private BigDecimal longitude;
}
