package ru.ifmo.se.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import ru.ifmo.se.dto.AttemptBean;
import ru.ifmo.se.models.AttemptEntity;
import ru.ifmo.se.services.AreaCheck;
import ru.ifmo.se.services.AreaCheckQualifier;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named("attemptRepository")
@ApplicationScoped
public class HitResultRepository implements Serializable {
    private static final int LATEST_ATTEMPTS_COUNT = 10;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    @AreaCheckQualifier
    private AreaCheck areaCheck;

    public List<AttemptEntity> getAttemptsList(int start, int count) {
        return entityManager.createQuery("select attempt from AttemptEntity attempt", AttemptEntity.class)
                .setFirstResult(start).setMaxResults(count).getResultList();
    }

    public List<AttemptEntity> getLatestAttemptsList() {
        int attemptsCount = getAttemptsCount();
        int firstResultIndex = Math.max(attemptsCount - LATEST_ATTEMPTS_COUNT, 0);
        return entityManager.createQuery("select attempt from AttemptEntity attempt", AttemptEntity.class)
                .setFirstResult(firstResultIndex).setMaxResults(LATEST_ATTEMPTS_COUNT).getResultList();
    }

    @Transactional
    public AttemptEntity addAttempt(AttemptBean attemptBean) {
        areaCheck.checkHit(attemptBean);
        AttemptEntity attemptEntity = attemptBean.toEntity();
        entityManager.merge(attemptEntity);
        //entityManager.persist(attemptEntity);
        //entityManager.flush();
        //entityManager.merge(attemptBean.toEntity());
        return attemptEntity;
    }

    public int getAttemptsCount() {
        return entityManager.createQuery("select count(*) from AttemptEntity", Number.class).getSingleResult().intValue();
    }

    @Transactional
    public void clearAttempts() {
        entityManager.createQuery("delete from AttemptEntity").executeUpdate();
    }

    @Transactional
    public void addAttemptFromJsParams(int currentR) {
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        try {
            double x = Double.parseDouble(params.get("x"));
            double y = Double.parseDouble(params.get("y"));
            double graphR = Double.parseDouble(params.get("r"));

            System.out.println("Received R value: " + currentR + " graphR: " + graphR);

            AttemptBean attemptBean = new AttemptBean(x / graphR * currentR, y / graphR * currentR, currentR);
            addAttempt(attemptBean);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public String collectToJson(Function<? super AttemptEntity, Double> getter) {
        return new Gson().toJson(getLatestAttemptsList().stream().map(getter).collect(Collectors.toList()));
    }

    public String getX() {
        return collectToJson(AttemptEntity::getX);
    }

    public String getY() {
        return collectToJson(AttemptEntity::getY);
    }

    public String getR() {
        return collectToJson(AttemptEntity::getR);
    }

    public String getHit() {
        return new Gson().toJson(getLatestAttemptsList().stream().map(AttemptEntity::isResult).collect(Collectors.toList()));
    }

    public String getPointsCoordinates() {
        return new Gson().toJson(
                getLatestAttemptsList().stream()
                        .map(attempt -> new AttemptBean.Coordinates(attempt.getX(), attempt.getY(), attempt.getR(), attempt.isResult()))
                        .collect(Collectors.toList())
        );
    }
}
