package com.koreatech.hangill.service;

import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.BuildFingerprintRequest;
import com.koreatech.hangill.dto.response.FingerprintResponse;

import java.util.List;

public interface NodeService {
    void validateDuplicatedNode(NodeSearch nodeSearch);
    void buildFingerPrint(BuildFingerprintRequest request);
    Node findOne(NodeSearch nodeSearch);

    List<FingerprintResponse> fingerprints(Long nodeId);

}
