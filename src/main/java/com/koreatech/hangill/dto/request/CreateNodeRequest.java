package com.koreatech.hangill.dto.request;

import com.koreatech.hangill.domain.NodeType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateNodeRequest {
    @NotNull(message = "노드 번호를 반드시 입력.")
    private Integer number;

    private String name;

    private String description;

    private NodeType type;

    @NotNull(message = "노드가 속한 층 수를 반드시 입력.")
    private Integer floor;
}
