package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.AddNodeToBuildingRequest;
import com.koreatech.hangill.exception.NoSuchNodeException;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NodeServiceImpl {
    private final NodeRepository nodeRepository;
    private final BuildingRepository buildingRepository;

    /**
     * 건물에 노드 추가
     * @param request : 건물 ID, 노드 정보
     * @return 저장한 노드 ID
     */
    public Long addNodeToBuilding(AddNodeToBuildingRequest request) {
        Building building = buildingRepository.findOne(request.getBuildingId());
        // 중복 노드 검증.
        validateDuplicatedNode(
                new NodeSearch(
                        request.getBuildingId(),
                        request.getNode().getNumber(),
                        request.getNode().getFloor()
                )
        );

        Node node = new Node(request.getNode());
        node.changeBuilding(building);
        nodeRepository.save(node);
        return node.getId();
    }

    /**
     * 중복 노드 검증
     * @param nodeSearch : 건물 ID, 층, 번호
     */

    @Transactional(readOnly = true)
    public void validateDuplicatedNode(NodeSearch nodeSearch) {
        if (nodeRepository.findAll(nodeSearch).size() > 0) throw new IllegalStateException("같은 건물의 같은 층에 해당 번호의 노드가 존재합니다.");
    }

    /**
     * 노드가 있는지 검증
     * @param nodeSearch : 건물 ID, 층, 번호
     */
    @Transactional(readOnly = true)
    public void validateHasNode(NodeSearch nodeSearch) {
        if (nodeRepository.findAll(nodeSearch).size() == 0) throw new NoSuchNodeException("해당 노드가 없습니다!");
    }

    /**
     * 조건을 통해 노드 한개 조회
     * @param nodeSearch : 건물 Id, 층, 번호
     * @return 노드
     */
    public Node findOne(NodeSearch nodeSearch) {
        try {
            return nodeRepository.findOne(nodeSearch);
        }catch (Exception e) {
            throw new NoSuchNodeException("건물에 헤당 노드가 없습니다!");
        }
    }


}
