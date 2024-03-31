package com.koreatech.hangill.repository;

import com.koreatech.hangill.domain.Edge;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.request.DeleteEdgeRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EdgeRepository {
    private final EntityManager em;

    public void save(Edge edge) {
        em.persist(edge);
    }

    public Edge findOne(Long id) {return em.find(Edge.class, id);}

    public List<Edge> findAll(DeleteEdgeRequest request) {
        String query = """
                SELECT e
                FROM Edge e JOIN e.building b
                                ON b.id = :buildingId
                            JOIN e.startNode sn
                                ON sn.number = :startNodeNumber
                                AND sn.floor = :startNodeFloor
                            JOIN e.endNode en
                                ON en.number = :endNodeNumber
                                AND en.floor = :endNodeFloor
                            
                """;

        return em.createQuery(query, Edge.class)
                .setParameter("buildingId", request.getBuildingId())
                .setParameter("startNodeNumber", request.getStartNodeNumber())
                .setParameter("startNodeFloor", request.getStartNodeFloor())
                .setParameter("endNodeNumber", request.getEndNodeNumber())
                .setParameter("endNodeFloor", request.getEndNodeFloor())
                .getResultList();
    }

    public List<Edge> findAll(Long buildingId) {
        String query = """
                SELECT e
                FROM Edge e JOIN FETCH e.building b
                            JOIN FETCH e.startNode sn
                            JOIN FETCH e.endNode en
                WHERE b.id = :buildingId
                """;

        return em.createQuery(query, Edge.class)
                .setParameter("buildingId", buildingId)
                .getResultList();
    }

}
