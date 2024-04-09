package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeleteNodeRequest {
    @Schema(
            description = "삭제할 노드의 건물 ID",
            example = "1"
    )
    private Long buildingId;
    @Schema(description = "삭제할 노드 ID", example = "1")

    private Long nodeId;
}
