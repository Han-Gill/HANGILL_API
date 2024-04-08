package com.koreatech.hangill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class NumberGraphResponse {
    private Map<Long, List<Long[]>> graph;
}
