package com.koreatech.hangill.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AddNodeToBuildingRequest {

    @NotEmpty(message = "반드시 건물 id 입력")
    private Long buildingId;
    private CreateNodeRequest createNodeRequest;

}
