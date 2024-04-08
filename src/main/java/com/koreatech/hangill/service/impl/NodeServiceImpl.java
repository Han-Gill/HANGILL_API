package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.domain.Fingerprint;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.BuildFingerprintRequest;
import com.koreatech.hangill.dto.request.SignalRequest;
import com.koreatech.hangill.dto.response.FingerprintResponse;
import com.koreatech.hangill.exception.NoSuchNodeException;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.repository.NodeRepository;
import com.koreatech.hangill.service.NodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.koreatech.hangill.domain.OperationStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;
    private final AccessPointRepository accessPointRepository;

    /**
     * 중복 노드 검증
     *
     * @param nodeSearch : 건물 ID, 층, 번호
     */

    @Transactional(readOnly = true)
    public void validateDuplicatedNode(NodeSearch nodeSearch) {
        if (nodeRepository.findAll(nodeSearch).size() > 0)
            throw new IllegalStateException("같은 건물의 같은 층에 해당 번호의 노드가 존재합니다.");
    }

    /**
     * 노드가 있는지 검증
     *
     * @param nodeSearch : 건물 ID, 층, 번호
     */
    @Transactional(readOnly = true)
    public void validateHasNode(NodeSearch nodeSearch) {
        if (nodeRepository.findAll(nodeSearch).size() == 0) throw new NoSuchNodeException("해당 노드가 없습니다!");
    }

    /**
     * 조건을 통해 노드 한 개 조회
     *
     * @param nodeSearch : 건물 Id, 층, 번호
     * @return 노드
     */
    @Transactional(readOnly = true)
    public Node findOne(NodeSearch nodeSearch) {
        try {
            return nodeRepository.findOne(nodeSearch);
        } catch (Exception e) {
            throw new NoSuchNodeException("건물에 헤당 노드가 없습니다!");
        }
    }

    /**
     * Node의 Fingerprint를 구축하는 메소드
     *
     * @param request : 노드 ID, wifi 신호목록
     *                1. AP filtering : 해당 건물에 있는 가동중인 AP들로 필터링
     *                2. node의 Fingerprint로 추가
     * 효율성 고려하지 않은 코드임. 추후 최적화 고려 필요
     */
    public void buildFingerPrint(BuildFingerprintRequest request) {
        Node node = nodeRepository.findOne(request.getNodeId());
        List<Fingerprint> fingerprints = new ArrayList<>();
        for (SignalRequest signal : request.getSignals()) {
            // 해당 건물의 해당 mac 주소를 가진 운용중인 AP를 찾기
            List<AccessPoint> accessPoints = accessPointRepository.findAll(node.getBuilding().getId(), signal.getMac(), RUNNING);
            // 없다면 고려 X
            if (accessPoints.size() == 0) continue;

            fingerprints.add(new Fingerprint(
                    accessPoints.get(0),
                    signal.getRssi(),
                    LocalDateTime.now()
            ));
        }

        node.buildFingerprints(fingerprints);
    }

    /**
     * 어떤 노드의 FingerPrint 목록을 반환
     * @param nodeId : 노드 ID
     * @return : (ssid, mac, rssi) 목록
     */
    @Transactional(readOnly = true)
    public List<FingerprintResponse> fingerprints(Long nodeId) {
        Node node = nodeRepository.findOne(nodeId);
        if (node == null) throw new IllegalArgumentException("해당 ID의 노드가 없습니다!");
        if (node.getFingerprints().size() == 0) throw new IllegalStateException("해당 노드에 Fingerprint가 구성되지 않았습니다!");

        return node.getFingerprints().stream()
                .map(FingerprintResponse::new)
                .collect(Collectors.toList());
    }


}
