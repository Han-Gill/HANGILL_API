package com.koreatech.hangill.repository;

import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.exception.BuildingNotFoundException;
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
        Building building = em.find(Building.class, id);
        if (building == null) throw BuildingNotFoundException.withDetail(String.valueOf(id));
        return building;
    }

    public Building findOne(String name) {
        String query = """
                SELECT b
                FROM Building b
                WHERE b.name = :name
                """;

        List<Building> buildings = em.createQuery(query, Building.class)
                .setParameter("name", name)
                .getResultList();
        if (buildings.size() == 0) throw BuildingNotFoundException.withDetail(name);
        return buildings.get(0);
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

    public List<Building> findAll() {
        String query = """
                SELECT b
                FROM Building b
                """;
        return em.createQuery(query, Building.class)
                .getResultList();
    }


}
