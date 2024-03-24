package com.koreatech.hangill.dto.request;

import lombok.Data;

@Data
public class DeleteNodeRequest {
    private Long buildingId;
    private Integer nodeNumber;
    private Integer nodeFloor;
}
