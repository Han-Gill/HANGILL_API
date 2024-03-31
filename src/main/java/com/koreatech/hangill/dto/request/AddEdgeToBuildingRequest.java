package com.koreatech.hangill.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddEdgeToBuildingRequest {
    private Long buildingId;
    private Integer startNodeNumber;
    private Integer startNodeFloor;
    private Integer endNodeNumber;
    private Integer endNodeFloor;
    private Long distance;
}
