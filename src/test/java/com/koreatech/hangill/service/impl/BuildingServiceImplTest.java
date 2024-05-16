package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.domain.Edge;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.*;
import com.koreatech.hangill.dto.response.NodePositionResponse;
import com.koreatech.hangill.dto.response.ShortestPathResponse;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.repository.EdgeRepository;
import com.koreatech.hangill.repository.NodeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
//@Rollback(value = false)
class BuildingServiceImplTest {

    @Autowired
    BuildingManagingServiceImpl buildingManagingService;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    BuildingServiceImpl buildingService;
    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    EdgeRepository edgeRepository;

    @PersistenceContext
    EntityManager em;


    @Test
    public void 모든_노드_조회() throws Exception {
        //given
        Long buildingId = buildGraph();
        //when
        List<Node> nodes = buildingService.findAllNodes(buildingId);
        //then

        List<Node> nodes2 = nodeRepository.findAllByBuilding(buildingId);
        assertEquals(nodes2, nodes);
    }


    @Test
    public void 모든_엣지_조회() throws Exception {
        //given
        Long buildingId = buildGraph();
        Building building = buildingRepository.findOne(buildingId);
        //when

        //then
        for (Edge edge : building.getEdges()) {
            System.out.println(edge.getStartNode().getNumber() + "--(" + edge.getDistance() + ")->" + edge.getEndNode().getNumber());
        }
        System.out.println("====================");
        for (Edge edge : edgeRepository.findAll(buildingId)) {
            System.out.println(edge.getStartNode().getNumber() + "--(" + edge.getDistance() + ")->" + edge.getEndNode().getNumber());
        }

    }

    @Test
    public void 그래프_조회() throws Exception {
        //given
        Long buildingId = buildGraph();

        //when
        Map<Long, List<Long[]>> numberGraph = buildingManagingService.findNumberGraph(buildingId);
        //then

        System.out.println(numberGraph.toString());
    }

    @Test
    public void 최단_경로_조회() throws Exception {
        //given
        Long buildingId = buildGraph();
        Node startNode = nodeRepository.findOne(new NodeSearch(
                buildingId, 1, 1)
        );
        Node endNode = nodeRepository.findOne(new NodeSearch(
                buildingId, 5, 1)
        );
        Node node = nodeRepository.findOne(new NodeSearch(
                buildingId, 4, 1
        ));

        //when
        ShortestPathResponse shortestPathResponse = buildingService.findPath(new ShortestPathRequest(
                buildingId,
                startNode.getNumber(),
                startNode.getFloor(),
                endNode.getNumber(),
                endNode.getFloor()
        ));
        List<NodePositionResponse> pathLabel = new ArrayList<>();
        pathLabel.add(new NodePositionResponse(startNode));
        pathLabel.add(new NodePositionResponse(node));
        pathLabel.add(new NodePositionResponse(endNode));

        //then
        assertEquals(pathLabel, shortestPathResponse.getPath());
        assertEquals(4, shortestPathResponse.getTotalDistance());

    }

    @Test
    public void 강의실_조회_테스트() throws Exception{
        //given
        Long buildingId = buildGraph();

        //when
        List<Node> allNodesByType = buildingService.findAllNodesByType(buildingId, NodeType.ROOM);

        //then
        for (Node node : allNodesByType) {
            assertEquals(NodeType.ROOM, node.getType());
        }

     }

    private Long buildGraph() {
        Long buildingId = buildingManagingService.saveBuilding(new CreateBuildingRequest(
                "공학2관",
                "컴공", null, null
        ));



        buildingManagingService.addNode(
                new AddNodeToBuildingRequest(
                        buildingId,
                        new CreateNodeRequest(
                                1, null, null, NodeType.ENTRANCE, 1
                        )
                )
        );
        buildingManagingService.addNode(
                new AddNodeToBuildingRequest(
                        buildingId,
                        new CreateNodeRequest(
                                2, null, null, NodeType.ROOM, 1
                        )
                )
        );
        buildingManagingService.addNode(
                new AddNodeToBuildingRequest(
                        buildingId,
                        new CreateNodeRequest(
                                3, null, null, NodeType.ROAD, 1
                        )
                )
        );
        buildingManagingService.addNode(
                new AddNodeToBuildingRequest(
                        buildingId,
                        new CreateNodeRequest(
                                4, null, null, NodeType.ROAD, 1
                        )
                )
        );
        buildingManagingService.addNode(
                new AddNodeToBuildingRequest(
                        buildingId,
                        new CreateNodeRequest(
                                5, null, null, NodeType.ROOM, 1
                        )
                )
        );
        buildingManagingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId, 1, 1, 2, 1, 2L
        ));
        buildingManagingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId, 1, 1, 3, 1, 3L
        ));
        buildingManagingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId, 1, 1, 4, 1, 1L
        ));
        buildingManagingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId, 1, 1, 5, 1, 10L
        ));
        buildingManagingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId, 2, 1, 4, 1, 2L
        ));
        buildingManagingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId, 3, 1, 4, 1, 1L
        ));
        buildingManagingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId, 3, 1, 5, 1, 1L
        ));
        buildingManagingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId, 4, 1, 5, 1, 3L
        ));

        return buildingId;
    }

}