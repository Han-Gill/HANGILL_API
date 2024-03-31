package com.koreatech.hangill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class ShortestPathResponse {
    public ShortestPathResponse(Long totalDistance, String path) {
        this.path = Stream.of(path.split(" "))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
        this.totalDistance = totalDistance;
    }
    private Long totalDistance;
    private List<Long> path;
}
