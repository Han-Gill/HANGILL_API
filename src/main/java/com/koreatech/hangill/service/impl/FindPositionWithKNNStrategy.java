package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.request.NodePositionRequest;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.repository.NodeRepository;
import com.koreatech.hangill.service.FindPositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.koreatech.hangill.domain.NodeType.ROAD;
import static com.koreatech.hangill.domain.OperationStatus.RUNNING;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPositionWithKNNStrategy extends FindPositionService {
    private final AccessPointRepository accessPointRepository;
    private final NodeRepository nodeRepository;
    private final int K = 5;

    @Override
    public Node findPosition(NodePositionRequest request) {
        return K_NearestNeighbor(request);
    }


    private Node K_NearestNeighbor(NodePositionRequest request) {
        // 먼저 건물에서 사용할 AP 목록 확보
        List<AccessPoint> runningAPs = accessPointRepository.findAll(request.getBuildingId(), RUNNING);
        List<Node> nodes = nodeRepository.findAll(request.getBuildingId(), ROAD);

        Queue<Position> queue = new PriorityQueue<>(new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return (int) (Math.round(o1.getWeight()) - Math.round(o2.getWeight()));
            }
        });

        // 각 노드에 대해 차이값 계산
        for (Node node : nodes) {
            Map<String, Integer> diff = buildDiff(runningAPs);
            double nodeScore = calculateWeight(diff, node, request.getSignals());
            queue.add(new Position(node, nodeScore));
        }

        List<Node> kPriorityNodes = new ArrayList<>();

        for (int i = 0; i < K; i++) {
            Position pos = queue.poll();
            if (pos == null) continue;
            kPriorityNodes.add(pos.getNode());
            log.info("\n<<<<<<<<<<<<<<<<<{}>>>>>>>>>>>>>>>>>>",
                    String.format("%d 번째로 선택된 노드, 가중치 : %f, 좌표 : x=%d y=%d", i, pos.getWeight(), pos.getNode().getX(), pos.getNode().getY()));
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



}
