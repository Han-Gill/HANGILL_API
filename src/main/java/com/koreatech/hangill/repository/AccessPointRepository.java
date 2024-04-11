package com.koreatech.hangill.repository;

import com.koreatech.hangill.domain.AccessPoint;
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
    public AccessPoint findOne(Long id) {return em.find(AccessPoint.class, id);}

    /**
     QueryDSL 학습 후 동적쿼리 작성으로 리펙토링 필요
     */
    public List<AccessPoint> findAll(Long buildingId, String mac, OperationStatus running) {
        String query = """
                SELECT a
                FROM AccessPoint a
                JOIN a.building b
                    ON b.id = :buildingId
                    AND a.mac = :mac
                    AND a.status = :running
                """;
        return em.createQuery(query, AccessPoint.class)
                .setParameter("buildingId", buildingId)
                .setParameter("mac", mac)
                .setParameter("running", running)
                .getResultList();
    }

    public List<AccessPoint> findAll(Long buildingId, OperationStatus status) {
        String query = """
                SELECT a
                FROM AccessPoint a
                JOIN a.building b
                    ON b.id = :buildingId
                    AND a.status = :status
                """;
        return em.createQuery(query, AccessPoint.class)
                .setParameter("buildingId", buildingId)
                .setParameter("status", status)
                .getResultList();
    }

    public List<AccessPoint> findAll(Long buildingId) {
        String query = """
                SELECT a
                FROM AccessPoint a
                JOIN a.building b
                    ON b.id = :buildingId
                """;
        return em.createQuery(query, AccessPoint.class)
                .setParameter("buildingId", buildingId)
                .getResultList();
    }

    public List<AccessPoint> findAll(String mac) {
        return em.createQuery("SELECT a FROM AccessPoint a WHERE a.mac = :mac", AccessPoint.class)
                .setParameter("mac", mac)
                .getResultList();
    }

    public List<AccessPoint> findAll(String ssid, Long buildingId) {
        String query = """
                SELECT a
                FROM AccessPoint a
                JOIN a.building b
                    ON b.id = :buildingId
                    AND a.ssid = :ssid
                """;
        return em.createQuery(query, AccessPoint.class)
                .setParameter("ssid", ssid)
                .setParameter("buildingId", buildingId)
                .getResultList();
    }
}

