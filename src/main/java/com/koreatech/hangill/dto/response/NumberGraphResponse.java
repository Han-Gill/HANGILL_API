package com.koreatech.hangill.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class NumberGraphResponse {
    @Schema(
            description = "건물의 그래프(노드 번호 기준)",
            example = """
                    {
                        "graph": {
                            "1": [
                                [6, 3],
                                [4, 3],
                                [2, 3]
                            ],
                            "2": [
                                [1, 3],
                                [6, 3],
                                [3, 3]
                            ],
                            "3": [
                                [5, 3]
                            ],
                            "4": [
                                [6, 3]
                            ],
                            "5": [
                                [3, 3],
                                [2, 3]
                            ],
                            "6": [
                                [1, 3]
                            ]
                        }
                    }
                    """
    )
    private Map<Long, List<Long[]>> graph;
}
