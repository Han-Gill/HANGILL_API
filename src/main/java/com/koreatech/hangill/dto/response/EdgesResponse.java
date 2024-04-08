package com.koreatech.hangill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EdgesResponse {
    private List<EdgeResponse> edges;
}
