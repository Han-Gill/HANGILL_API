package com.koreatech.hangill.dto.response;

import com.koreatech.hangill.dto.NodeDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NodeDtosResponse {
    private List<NodeDto> nodes;
}
