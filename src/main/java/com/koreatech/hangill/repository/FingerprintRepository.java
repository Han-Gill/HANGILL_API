package com.koreatech.hangill.repository;

import com.koreatech.hangill.domain.Fingerprint;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FingerprintRepository {
    private final EntityManager em;

    public void save(Fingerprint fingerprint) {
        em.persist(fingerprint);
    }
}
