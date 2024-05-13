package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.service.BuildingManagingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
@Transactional
class InputServiceTest {
    @Autowired
    InputService inputService;

    @Autowired
    BuildingManagingService buildingService;
    @Test
    @Rollback(value = false)
    public void dwd() throws Exception{

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
}