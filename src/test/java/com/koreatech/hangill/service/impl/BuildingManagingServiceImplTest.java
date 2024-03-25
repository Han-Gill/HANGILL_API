package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.domain.Edge;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import com.koreatech.hangill.dto.request.AddEdgeToBuildingRequest;
import com.koreatech.hangill.dto.request.AddNodeToBuildingRequest;
import com.koreatech.hangill.dto.request.CreateBuildingRequest;
import com.koreatech.hangill.dto.request.CreateNodeRequest;
import com.koreatech.hangill.exception.NoSuchNodeException;
import com.koreatech.hangill.repository.BuildingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class BuildingManagingServiceImplTest {

    @Autowired
    BuildingManagingServiceImpl buildingService;
    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    BuildingManagingServiceImpl buildingManagingService;
    @PersistenceContext
    EntityManager em;

    @Test
    public void 건물에서_노드_추가() throws Exception {
        //given
        Long buildingId = saveBuilding();


        CreateNodeRequest createNodeRequest = new CreateNodeRequest(1, null, null, null, 1);
        CreateNodeRequest createNodeRequest2 = new CreateNodeRequest(2, null, null, null, 1);
        CreateNodeRequest createNodeRequest3 = new CreateNodeRequest(3, null, null, null, 1);


        //when
        buildingService.addNode(new AddNodeToBuildingRequest(
                buildingId,
                createNodeRequest
        ));
        buildingService.addNode(new AddNodeToBuildingRequest(
                buildingId,
                createNodeRequest2
        ));
        buildingService.addNode(new AddNodeToBuildingRequest(
                buildingId,
                createNodeRequest3
        ));

        em.flush();
        em.clear();
        //then
        // 테스트 결과 컬렉션의 요소 하나에 대해 데이터 요청만해도 모든 요소들의 Proxy가 초기화됨.
        System.out.println("===================================");
        Building building = buildingRepository.findOne(buildingId);
        System.out.println(building.getNodes().get(0).getNumber());
        System.out.println(building.getNodes().get(0).getClass());
        System.out.println(building.getNodes().get(1).getClass());
        System.out.println(building.getNodes().get(2).getClass());

        List<Node> rooms = building.getNodes().stream()
                .filter(node -> node.getType() == NodeType.ROOM).toList();

    }

    @Test
    public void 건물_엣지_추가() throws Exception {
        //given
        Long buildingId = saveBuilding();

        buildingService.addNode(new AddNodeToBuildingRequest(
                buildingId,
                new CreateNodeRequest(1, null, null, null, 3)
        ));
        buildingService.addNode(new AddNodeToBuildingRequest(
                buildingId,
                new CreateNodeRequest(2, null, null, null, 3)
        ));
        buildingService.addNode(new AddNodeToBuildingRequest(
                buildingId,
                new CreateNodeRequest(3, null, null, null, 3)
        ));

        //when
        buildingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId,
                1, 3, 3, 3, 100L
        ));
        buildingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId,
                2, 3, 3, 3, 100L
        ));
        buildingService.addEdge(new AddEdgeToBuildingRequest(
                buildingId,
                2, 3, 1, 3, 100L
        ));

        //then

        em.flush();
        em.clear();

        Building building = buildingRepository.findOne(buildingId);
        for (Edge edge : building.getEdges()) {
            System.out.println(edge.getDistance());
            System.out.println(edge.getStartNode().getNumber() + "->" + edge.getEndNode().getNumber());
        }

        building.getNodes().remove(0);

    }

    private Long saveBuilding() {
        Long buildingId = buildingService.saveBuilding(
                new CreateBuildingRequest("공학 2관", "컴공", null, null)
        );
        return buildingId;
    }

    @Test
    public void 엣지_예외_테스트() throws Exception {
        //given
        Long buildingId = buildGraph("공학 1관");

        Assertions.assertThrows(NoSuchNodeException.class, () -> {
            buildingManagingService.addEdge(
                    new AddEdgeToBuildingRequest(
                            buildingId, 1, 1, 6, 3, 100L)

            );
        });
        //when

        //then


    }

    private Long buildGraph(String buildingName) {
        Long buildingId = buildingManagingService.saveBuilding(new CreateBuildingRequest(
                buildingName,
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