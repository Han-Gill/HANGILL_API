package com.koreatech.hangill.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class AccessPointRepositoryTest {
    @Autowired
    AccessPointRepository accessPointRepository;
    @Test
    public void 조회쿼리_확인() throws Exception{
        //given
        //when
        //then

     }

}