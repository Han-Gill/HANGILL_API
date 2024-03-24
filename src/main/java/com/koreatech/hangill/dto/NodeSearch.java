package com.koreatech.hangill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NodeSearch {
    private Long buildingId;
    private Integer number;
    private Integer floor;
}
