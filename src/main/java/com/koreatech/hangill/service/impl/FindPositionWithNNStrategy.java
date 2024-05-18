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

//@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FindPositionWithNNStrategy extends FindPositionService {
    private final AccessPointRepository accessPointRepository;
    private final NodeRepository nodeRepository;
    @Override
    public Node findPosition(NodePositionRequest request) {
        return NearestNeighbor(request);
    }
    private Node NearestNeighbor(NodePositionRequest request) {
        // 먼저 건물에서 사용할 AP 목록 확보
        List<AccessPoint> runningAPs = accessPointRepository.findAll(request.getBuildingId(), RUNNING);
        List<Node> nodes = nodeRepository.findAll(request.getBuildingId(), ROAD);

        Queue<Position> queue = new PriorityQueue<>(new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return (int)(Math.round(o1.getWeight()) - Math.round(o2.getWeight()));
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
                    String.format("%d 번째로 선택된 노드, 가중치 : %f", i, pos.getWeight()));
            loggingSignal(request, pos.getNode(), runningAPs.stream().map(AccessPoint::getMac).collect(Collectors.toSet()));
        }
        return minNode;
    }


}
