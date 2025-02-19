package ru.ifmo.se.repositories;

import com.google.gson.Gson;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ru.ifmo.se.models.Attempt;
import ru.ifmo.se.services.AreaChekcing.AreaCheck;
import ru.ifmo.se.services.AreaChekcing.AreaCheckQualifier;
import ru.ifmo.se.services.Validation.ValidatePointsQualifier;
import ru.ifmo.se.services.Validation.ValidationPoints;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//Controller - Service - Repository
//

@Named("attemptRepository")
@ApplicationScoped
public class HitResultRepository implements Serializable {

    private static final int LATEST_ATTEMPTS_COUNT = 10;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Attempt> getAttemptsList(int start, int count) {
        return entityManager.createQuery("select attempt from Attempt attempt", Attempt.class)
                .setFirstResult(start).setMaxResults(count).getResultList();
    }

    public List<Attempt> getLatestAttemptsList() {
        int attemptsCount = getAttemptsCount();
        int firstResultIndex = Math.max(attemptsCount - LATEST_ATTEMPTS_COUNT, 0);
        return  entityManager.createQuery("select attempt From Attempt attempt", Attempt.class)
                .setFirstResult(firstResultIndex).setMaxResults(LATEST_ATTEMPTS_COUNT).getResultList();
    }

    public int getAttemptsCount() {
        return entityManager.createQuery("select count(*) from Attempt", Number.class).getSingleResult().intValue();
    }

    @Transactional
    public void clearAttempts() {
        entityManager.createQuery("delete from Attempt").executeUpdate();
    }


}
