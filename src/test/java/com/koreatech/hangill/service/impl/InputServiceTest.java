package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.domain.NodeType;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.AddNodeToBuildingRequest;
import com.koreatech.hangill.dto.request.CreateNodeRequest;
import com.koreatech.hangill.repository.NodeRepository;
import com.koreatech.hangill.service.BuildingManagingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;


@SpringBootTest
@Transactional
class InputServiceTest {
    @Autowired
    InputService inputService;

    @Autowired
    BuildingManagingService buildingService;

    @Autowired
    NodeRepository nodeRepository;

    @Test
    @Rollback(value = false)
    public void dwd() throws Exception {

//        buildingService.saveBuilding(new CreateBuildingRequest(
//                "공학 2관",
//                "컴퓨터 공학부 학생들과 건축 디자인 공학부 학생들이 전공수업을 듣는 곳입니다.",
//                new BigDecimal("127.2816568"),
//                new BigDecimal("36.7667765")
//        ));

//        inputService.initData();
//        inputService.saveAccessPoints();
//        inputService.saveFingerPrint();
//        inputService.find();
        //when

        //then

    }

    @Test
    @Rollback(value = false)
    public void KNN_노드_추가() throws Exception {
//        //given
//        for (int num = 500; num <= 535; num++) {
//            buildingService.addNode(new AddNodeToBuildingRequest(1L, new CreateNodeRequest(
//                    num, "복도", "복도", NodeType.ROAD, 4
//            )));
//        }
//        //when
//
//        //then

    }

    @Test
    @Rollback(value = false)
    public void KNN_복도_노드에_좌표_추가() throws Exception{
//        //given
//        String path = "/Users/jeongseong-o/2024-1/hanGill/map/node_coordinates";
//        BufferedReader br = new BufferedReader(new FileReader(path + "/gong2_4_nodes.txt"));
//        String str;
//        while ((str = br.readLine()) != null) {
//            String[] tmp = str.split(", ");
//            Long buildingId = 1L;
//            Integer floor = 4;
//            Integer number = Integer.parseInt(tmp[0]);
//            Integer y = Integer.parseInt(tmp[1]);
//            Integer x = Integer.parseInt(tmp[2]);
//            Node node = null;
//            try {
//                node = nodeRepository.findOne(new NodeSearch(buildingId, number, floor));
//            }catch (EmptyResultDataAccessException ex) {
//                System.out.println("없는 노드 : " + floor + "층 " + number + "번");
//                continue;
//            }
//            if (node.getType() != NodeType.ROAD) continue;
//            node.updateCoordinates(y, x);
//        }
//        br.close();


        //when

        //then

     }
}