package com.koreatech.hangill.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodePositionRequest {
    @NotNull(message = "반드시 건물 ID를 입력해주세요!")
    @Schema(description = "위치를 조회할 노드가 속한 건물 ID", example = "1")
    private Long buildingId;
    private List<SignalRequest> signals;
}
