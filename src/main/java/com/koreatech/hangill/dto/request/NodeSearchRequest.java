package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeSearchRequest {
    @NotBlank(message = "건물 이름은 필수로 입력해주세요")
    @Schema(description = "찾을 노드가 속한 건물 이름", example = "공학 2관")
    private String buildingName;

    @NotNull(message = "노드의 번호는 필수로 입력해주세요")
    @Schema(description = "찾을 노드의 번호", example = "1")
    private Integer number;

    @NotNull(message = "노드의 층수는 필수로 입력해주세요")
    @Schema(description = "찾을 노드의 층", example = "2")
    private Integer floor;
}
