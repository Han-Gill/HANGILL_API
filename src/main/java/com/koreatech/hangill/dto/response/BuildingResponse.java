package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.domain.Building;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuildingResponse {
    public BuildingResponse(Building building) {
        this.id = building.getId();
        this.name = building.getName();
        this.description = building.getDescription();
        this.latitude = building.getLatitude();
        this.longitude = building.getLongitude();
    }
    private Long id;
    private String name;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
