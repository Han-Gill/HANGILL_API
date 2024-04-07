package com.koreatech.hangill.repository;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.domain.OperationStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class AccessPointRepository {
    private final EntityManager em;

    public void save(AccessPoint accessPoint) {
        em.persist(accessPoint);
    }


    public List<AccessPoint> findAll(Long buildingId, String mac, OperationStatus running) {
        String query = """
                SELECT a
                FROM AccessPoint a
                JOIN a.building b
                    ON b.id = :buildingId
                WHERE a.mac = :mac
                    AND a.status = :running
                """;
        return em.createQuery(query, AccessPoint.class)
                .setParameter("buildingId", buildingId)
                .setParameter("mac", mac)
                .setParameter("running", running)
                .getResultList();
    }
}

