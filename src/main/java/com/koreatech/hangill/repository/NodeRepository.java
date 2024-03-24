package com.koreatech.hangill.repository;

import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
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


}
