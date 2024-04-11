package com.koreatech.hangill.repository;

import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.exception.NodeNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NodeRepository {
    private final EntityManager em;

    public void save(Node node) {
        em.persist(node);
    }

    public List<Node> findAllByBuilding(Long buildingId) {
        String query = """
                SELECT n
                FROM Node n JOIN n.building b
                        ON b.id = :buildingId
                """;
        return em.createQuery(query, Node.class)
                .setParameter("buildingId", buildingId)
                .getResultList();
    }

    public List<Node> findAll(NodeSearch nodeSearch) {
        String query = """
                SELECT n
                FROM Node n JOIN n.building b
                        ON b.id = :buildingId
                        AND n.number = :number
                        AND n.floor = :floor
                """;
        return em.createQuery(query, Node.class)
                .setParameter("buildingId", nodeSearch.getBuildingId())
                .setParameter("number", nodeSearch.getNumber())
                .setParameter("floor", nodeSearch.getFloor())
                .getResultList();
    }
    public Node findOne(NodeSearch nodeSearch) {
    String query = """
            SELECT n
            FROM Node n JOIN n.building b
                    ON b.id = :buildingId
                    AND n.number = :number
                    AND n.floor = :floor
            """;
    return em.createQuery(query, Node.class)
            .setParameter("buildingId", nodeSearch.getBuildingId())
            .setParameter("number", nodeSearch.getNumber())
            .setParameter("floor", nodeSearch.getFloor())
            .getSingleResult();
    }

    public Node findOne(Long id) {
        Node node = em.find(Node.class, id);
        if (node == null) throw NodeNotFoundException.withDetail(id + "번 ID를 가진 ");
        return node;
    }

    // 페치조인 최적화
    public List<Node> findAll(Long buildingId) {
        String query = """
                SELECT DISTINCT n
                FROM Node n JOIN FETCH n.fingerprints f
                            JOIN FETCH f.accessPoint a
                            JOIN FETCH n.building b
                WHERE b.id = :buildingId
                
                """;

        return em.createQuery(query, Node.class)
                .setParameter("buildingId", buildingId)
                .getResultList();
    }


}
