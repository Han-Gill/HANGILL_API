package com.koreatech.hangill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NodesResponse {
    private List<NodeResponse> nodes;
}
