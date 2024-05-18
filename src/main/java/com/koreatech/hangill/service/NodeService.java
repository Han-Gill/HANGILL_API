package com.koreatech.hangill.service;

import com.koreatech.hangill.domain.Fingerprint;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.BuildFingerprintRequest;
import com.koreatech.hangill.dto.request.NodePositionRequest;
import com.koreatech.hangill.dto.response.FingerprintResponse;

import java.util.List;

public interface NodeService {
    void validateDuplicatedNode(NodeSearch nodeSearch);
    void buildFingerPrint(BuildFingerprintRequest request);
    void buildAccessPointAndFingerPrint(BuildFingerprintRequest request);
    Node findOne(NodeSearch nodeSearch);
    List<Fingerprint> fingerprints(Long nodeId);
//    Node findPosition(NodePositionRequest request);
//    Node findPositionV2(NodePositionRequest request);

}
