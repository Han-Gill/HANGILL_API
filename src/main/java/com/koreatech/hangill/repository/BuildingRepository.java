package com.koreatech.hangill.repository;

import com.koreatech.hangill.domain.Building;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BuildingRepository {
    private final EntityManager em;

    public void save(Building building) {
        em.persist(building);
    }

    public Building findOne(Long id) {
        return em.find(Building.class, id);
    }

    public void deleteOne(Building building) {
        em.remove(building);
    }

    public List<Building> findAll(String name) {
        String query = """
                SELECT b
                FROM Building b
                WHERE b.name = :name
                """;

        return em.createQuery(query, Building.class)
                .setParameter("name", name)
                .getResultList();
    }


}
