package com.koreatech.hangill.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FingerprintsResponse {
    @Schema(description = "핑거프린트 목록")
    private List<FingerprintResponse> fingerprints;
}
