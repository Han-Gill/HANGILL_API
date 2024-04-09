package com.koreatech.hangill.dto.request;

import com.koreatech.hangill.domain.NodeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateNodeRequest {
    @NotNull(message = "노드 번호를 반드시 입력.")
    @Schema(
            description = "추가할 노드의 번호",
            nullable = false,
            example = "1"
    )
    private Integer number;

    @Schema(
            description = "추가할 노드의 이름",
            nullable = false,
            example = "319호"
    )
    private String name;

    @Schema(
            description = "추가할 노드의 세부 설명",
            nullable = false,
            example = "XXX 교수님의 연구실"
    )
    private String description;

    @Schema(
            description = "추가할 노드의 타입",
            nullable = false,
            example = "ROOM"
    )
    private NodeType type;

    @Schema(
            description = "추가할 노드의 층 수",
            nullable = false,
            example = "3"
    )
    @NotNull(message = "노드가 속한 층 수를 반드시 입력.")
    private Integer floor;
}
