package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.domain.Fingerprint;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.BuildFingerprintRequest;
import com.koreatech.hangill.dto.request.NodePositionRequest;
import com.koreatech.hangill.dto.request.SignalRequest;
import com.koreatech.hangill.exception.AccessPointNotFoundException;
import com.koreatech.hangill.exception.NodeNotFoundException;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.repository.NodeRepository;
import com.koreatech.hangill.service.AccessPointService;
import com.koreatech.hangill.service.NodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.koreatech.hangill.domain.OperationStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;
    private final AccessPointRepository accessPointRepository;
    private final AccessPointService accessPointService;

    /**
     * 중복 노드 검증
     *
     * @param nodeSearch : 건물 ID, 층, 번호
     */

    @Transactional(readOnly = true)
    public void validateDuplicatedNode(NodeSearch nodeSearch) {
        if (nodeRepository.findAll(nodeSearch).size() > 0)
            throw new IllegalArgumentException("같은 건물의 같은 층에 해당 번호의 노드가 존재합니다.");
    }

    /**
     * 노드가 있는지 검증
     *
     * @param nodeSearch : 건물 ID, 층, 번호
     */
    @Transactional(readOnly = true)
    public void validateHasNode(NodeSearch nodeSearch) {
        if (nodeRepository.findAll(nodeSearch).size() == 0) {
            throw NodeNotFoundException.withDetail(String.valueOf(nodeSearch.getNumber()));
        }
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
            String message = nodeSearch.getBuildingId() + "번 ID 건물에 " + nodeSearch.getFloor() +"층에 속한 "
                    + nodeSearch.getNumber() + "번";
            throw NodeNotFoundException.withDetail(message);
        }
    }

    /**
     * Node의 Fingerprint를 구축하는 메소드
     *
     * @param request : 노드 ID, wifi 신호목록
     *                node의 Fingerprint로 추가
     * 효율성 고려하지 않은 코드임. 추후 최적화 고려 필요
     */
    public void buildFingerPrint(BuildFingerprintRequest request) {
        Node node = nodeRepository.findOne(request.getNodeId());
        List<Fingerprint> fingerprints = new ArrayList<>();
        for (SignalRequest signal : request.getSignals()) {
            List<AccessPoint> accessPoints = accessPointRepository.findAll(signal.getMac());
            // DB에 저장되어 있지 않은 AP로 부터 받은 신호는 무시.
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
     * 받은 신호들로부터 AccessPoint들을 모두 저장한 후 Fingerprint저장.
     * @param request
     */
    public void buildAccessPointAndFingerPrint(BuildFingerprintRequest request) {
        accessPointService.saveAllBySignals(request);
        buildFingerPrint(request);
    }

    /**
     * 어떤 노드의 FingerPrint 목록을 반환
     * @param nodeId : 노드 ID
     * @return : (ssid, mac, rssi) 목록
     */
    @Transactional(readOnly = true)
    public List<Fingerprint> fingerprints(Long nodeId) {
        Node node = nodeRepository.findOne(nodeId);
        if (node == null) throw NodeNotFoundException.withDetail(nodeId + "번 ID를 가진");

        return node.getFingerprints();
    }
    /**
     * 건물 ID와 받은 신호세기목록을 바탕으로 현재위치와 가장 가까운 노드가 어떤 노드인지 판별
     * @param request : 건물 ID, 신호세기 목록
     * @return : 위치를 판정한 노드 ID
     * 일단 최적화 고려하지 않고 짜보자. => 추후 Fetch Join으로 최적화 수행!
     */
    @Transactional(readOnly = true)
    public Node findPosition(NodePositionRequest request) {
        // 먼저 건물에서 사용할 AP 목록 확보
        List<AccessPoint> runningAPs = accessPointRepository.findAll(request.getBuildingId(), RUNNING);
        List<Node> nodes = nodeRepository.findAll(request.getBuildingId());

        Node minNode = null;
        double minVal = Double.MAX_VALUE;

        // 각 노드에 대해 차이값 계산
        for (Node node : nodes) {
            Map<String, Integer> diff = buildDiff(runningAPs);
            double nodeScore = calculateWeight(diff, node, request.getSignals());
            if (nodeScore < minVal) {
                minNode = node;
                minVal = nodeScore;
            }
        }
        return minNode;
    }
    private Map<String, Integer> buildDiff(List<AccessPoint> aps) {
        Map<String, Integer> diff = new HashMap<>();
        for (AccessPoint accessPoint : aps) {
            diff.put(accessPoint.getMac(), 0);
        }
        return diff;
    }
    private double calculateWeight(Map<String, Integer> diff, Node node, List<SignalRequest> signals) {
        List<Fingerprint> fingerprints = node.getFingerprints();
        for (Fingerprint fingerprint : fingerprints) {
            String mac = fingerprint.getAccessPoint().getMac();
            if (diff.containsKey(mac)) diff.put(mac, diff.get(mac) + fingerprint.getRssi());
        }
        for (SignalRequest signal : signals) {
            String mac = signal.getMac();
            if (diff.containsKey(mac)) diff.put(mac, diff.get(mac) - signal.getRssi());
        }

        int total_score = 0;
        for (Integer score : diff.values()) {
            total_score += score * score;
        }
        return Math.sqrt(total_score);
    }
}
