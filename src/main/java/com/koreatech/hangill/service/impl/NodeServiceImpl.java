package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.domain.Fingerprint;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.BuildFingerprintRequest;
import com.koreatech.hangill.dto.request.NodePositionRequest;
import com.koreatech.hangill.dto.request.SignalRequest;
import com.koreatech.hangill.exception.NodeDuplicatedException;
import com.koreatech.hangill.exception.NodeNotFoundException;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.repository.NodeRepository;
import com.koreatech.hangill.service.AccessPointService;
import com.koreatech.hangill.service.NodeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.koreatech.hangill.domain.NodeType.*;
import static com.koreatech.hangill.domain.OperationStatus.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;
    private final AccessPointRepository accessPointRepository;
    private final AccessPointService accessPointService;

    private final int RSSI_OFFSET = 100;

    /**
     * 중복 노드 검증
     *
     * @param nodeSearch : 건물 ID, 층, 번호
     */

    @Transactional(readOnly = true)
    public void validateDuplicatedNode(NodeSearch nodeSearch) {
        if (nodeRepository.findAll(nodeSearch).size() > 0)
            throw NodeDuplicatedException.withDetail(nodeSearch.getFloor() + "층 " + nodeSearch.getNumber() +"번 ");
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
     * @param request : 노드 ID, wifi 신호목록
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
     * 건물 ID와 받은 신호세기목록을 바탕으로 현재위치와 가장 가까운 노드가 어떤 노드인지 판별 Version2
     * 1. 복도 노드로만 필터링
     * 2. 각 노드에 대해
     *     비교 AP Set 선정.
     *         - 노드와 신호의 합집합.
     *         - 사용하는 AP들로만 필터링.
     *     신호 거리값 계산
     * 3. 최적의 노드를 찾음.
     * @param request : 건물 ID와 신호들
     * @return 사용자의 신호세기로부터 가장 가까운 노드
     */
    @Transactional(readOnly = true)
    public Node findPositionV2(NodePositionRequest request) {
        // 사용하는 AP들.
        Set<String> runningAPs = accessPointRepository.findAll(request.getBuildingId(), RUNNING).stream()
                .map(AccessPoint::getMac)
                .collect(Collectors.toSet());
        Map<String, Integer> userSignals = request.getSignals().stream()
                .collect(Collectors.toMap(SignalRequest::getMac, SignalRequest::getRssi));
        // 노드들
        List<Node> nodes = nodeRepository.findAll(request.getBuildingId(), ROAD);

        Node minNode = null;
        double minVal = Double.MAX_VALUE;

        for (Node node : nodes) {
            double nodeScore = calculateScore(userSignals, getNodeSignals(node), runningAPs);
            if (minVal > nodeScore) {
                minNode = node;
                minVal = nodeScore;
            }
        }

        loggingSignal(request, minNode, runningAPs);
        return minNode;
    }

    private void loggingSignal(NodePositionRequest request, Node node, Set<String> runningAPs) {
        StringBuilder logging = new StringBuilder();
        logging.append("\n==========================User Info==========================").append("\n");
        for (SignalRequest signal : request.getSignals()) {
            if (runningAPs.contains(signal.getMac())) logging.append(signal).append("\n");
        }

        logging.append(String.format("==========================Node %d floor %d==========================", node.getNumber(), node.getFloor())).append("\n");
        for (Fingerprint fingerprint : node.getFingerprints()) {
            if (runningAPs.contains(fingerprint.getAccessPoint().getMac())) logging.append("ssid : ").append(fingerprint.getAccessPoint().getSsid()).append(" ")
                    .append("mac : ").append(fingerprint.getAccessPoint().getMac()).append(" ")
                    .append("rssi : ").append(fingerprint.getRssi()).append(" \n");
        }
        log.info("{}", logging);
    }

    private double calculateScore(
            Map<String, Integer> userSignals,
            Map<String, Integer> nodeSignals,
            Set<String> runningAps
            ) {

        double total_score = 0;
        Set<String> comparingAPs = new HashSet<>();
        comparingAPs.addAll(userSignals.keySet());
        comparingAPs.addAll(nodeSignals.keySet());
        comparingAPs.retainAll(runningAps);

        for (String AP : comparingAPs) {
//            double score = (userSignals.getOrDefault(AP, 0) + RSSI_OFFSET) - (nodeSignals.getOrDefault(AP, 0) + RSSI_OFFSET);
            double score = 0;
            if (userSignals.containsKey(AP)) score += (userSignals.get(AP) + RSSI_OFFSET);
            if (nodeSignals.containsKey(AP)) score -= (nodeSignals.get(AP) + RSSI_OFFSET);
            total_score += score * score;
        }
        return Math.sqrt(total_score);
    }

    private Map<String, Integer> getNodeSignals(Node node) {
        Map<String, Integer> nodeSignals = new HashMap<>();
        for (Fingerprint fingerprint : node.getFingerprints()) {
            nodeSignals.put(fingerprint.getAccessPoint().getMac(), fingerprint.getRssi());
        }
        return nodeSignals;
    }


    /**
     * 건물 ID와 받은 신호세기목록을 바탕으로 현재위치와 가장 가까운 노드가 어떤 노드인지 판별
     * @param request : 건물 ID, 신호세기 목록
     * @return : 위치를 판정한 노드 ID
     * 일단 최적화 고려하지 않고 짜보자. => 추후 Fetch Join으로 최적화 수행!
     */
    @Transactional(readOnly = true)
    public Node findPosition(NodePositionRequest request) {
        return K_NearestNeighbor(request);
    }


    @Data
    @AllArgsConstructor
    class Position {
        private Node node;
        private double weight;
    }
    private Node K_NearestNeighbor(NodePositionRequest request) {
        // 먼저 건물에서 사용할 AP 목록 확보
        List<AccessPoint> runningAPs = accessPointRepository.findAll(request.getBuildingId(), RUNNING);
        List<Node> nodes = nodeRepository.findAll(request.getBuildingId(), ROAD);

        Queue<Position> queue = new PriorityQueue<>(new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return (int)(Math.round(o1.weight) - Math.round(o2.weight));
            }
        });

        // 각 노드에 대해 차이값 계산
        for (Node node : nodes) {
            Map<String, Integer> diff = buildDiff(runningAPs);
            double nodeScore = calculateWeight(diff, node, request.getSignals());
            queue.add(new Position(node, nodeScore));
        }

        List<Node> kPriorityNodes = new ArrayList<>();

        int k = 5;
        for (int i = 0; i < k; i ++) {
            Position pos = queue.poll();
            if (pos == null) continue;
            kPriorityNodes.add(pos.node);
            log.info("\n<<<<<<<<<<<<<<<<<{}>>>>>>>>>>>>>>>>>>",
                    String.format("%d 번째로 선택된 노드, 가중치 : %f, 좌표 : x=%d y=%d", i, pos.weight, pos.node.getX(), pos.node.getY()));
            loggingSignal(request, pos.getNode(), runningAPs.stream().map(AccessPoint::getMac).collect(Collectors.toSet()));
        }
        return determineNode(kPriorityNodes);
    }

    private Node determineNode(List<Node> kPriorityNodes) {
        double x = 0; double y = 0;
        int k = kPriorityNodes.size();
        for (Node node : kPriorityNodes) {
            x += node.getX();
            y += node.getY();
        }
        x /= k; y /= k;

        Node selectedNode = null;
        double minVal = Double.MAX_VALUE;
        for (Node node : kPriorityNodes) {
            double score = euclideanDistance(x, node.getX(), y, node.getY());
            if (minVal > score) {
                minVal = score;
                selectedNode = node;
            }
        }
        return selectedNode;
    }

    private double euclideanDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }



    private Node NearestNeighbor(NodePositionRequest request) {
        // 먼저 건물에서 사용할 AP 목록 확보
        List<AccessPoint> runningAPs = accessPointRepository.findAll(request.getBuildingId(), RUNNING);
        List<Node> nodes = nodeRepository.findAll(request.getBuildingId(), ROAD);

        Queue<Position> queue = new PriorityQueue<>(new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return (int)(Math.round(o1.weight) - Math.round(o2.weight));
            }
        });

        // 각 노드에 대해 차이값 계산
        for (Node node : nodes) {
            Map<String, Integer> diff = buildDiff(runningAPs);
            double nodeScore = calculateWeight(diff, node, request.getSignals());
            queue.add(new Position(node, nodeScore));
        }

        assert queue.peek() != null;
        Node minNode = queue.peek().getNode();
        int k = 5;
        for (int i = 0; i < k; i ++) {
            Position pos = queue.poll();
            if (pos == null) continue;
            log.info("\n<<<<<<<<<<<<<<<<<{}>>>>>>>>>>>>>>>>>>",
                    String.format("%d 번째로 선택된 노드, 가중치 : %f", i, pos.weight));
            loggingSignal(request, pos.getNode(), runningAPs.stream().map(AccessPoint::getMac).collect(Collectors.toSet()));
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
            if (diff.containsKey(mac)) diff.put(mac, diff.get(mac) + (fingerprint.getRssi() + RSSI_OFFSET));
        }
        for (SignalRequest signal : signals) {
            String mac = signal.getMac();
            if (diff.containsKey(mac)) diff.put(mac, diff.get(mac) - (signal.getRssi() + RSSI_OFFSET));
        }

        int total_score = 0;
        for (Integer score : diff.values()) {
            total_score += score * score;
        }
        return Math.sqrt(total_score);
    }
}
