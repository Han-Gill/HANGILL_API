package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindNodeForNameRequest {

    @NotNull(message = "반드시 건물 id 입력")
    @Schema(
            description = "찾을 노드의 건물 ID",
            example = "1"
    )
    private Long buildingId;
    @Schema(
            description = "찾을 노드의 이름",
            example = "319호"
    )
    private String nodeName;
}
