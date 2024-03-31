package com.koreatech.hangill.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortestPathRequest {
    private Long buildingId;
    private Long startNodeId;
    private Long endNodeId;
}
