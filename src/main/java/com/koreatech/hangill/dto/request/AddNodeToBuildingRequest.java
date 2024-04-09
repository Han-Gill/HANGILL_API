package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AddNodeToBuildingRequest {

    @NotNull(message = "반드시 건물 id 입력")
    @Schema(
            description = "노드를 추가할 건물 ID",
            nullable = false,
            example = "1"
    )
    private Long buildingId;

    private CreateNodeRequest node;

}
