package ru.ifmo.se.controller;

import com.google.gson.Gson;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import ru.ifmo.se.models.Attempt;
import ru.ifmo.se.services.AreaChekcing.AreaCheck;
import ru.ifmo.se.services.AreaChekcing.AreaCheckQualifier;
import ru.ifmo.se.services.Validation.ValidatePointsQualifier;
import ru.ifmo.se.services.Validation.ValidationPoints;
import ru.ifmo.se.repositories.HitResultRepository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
@Named("hitResultController")
public class HitResultController {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    @AreaCheckQualifier
    private AreaCheck areaCheck;

    @Inject
    @ValidatePointsQualifier
    private ValidationPoints validatePoints;

    public boolean validateAttempt(Attempt attempt) {
        return validatePoints.validate(attempt.getX(), attempt.getY(), attempt.getR());
    }

    @Transactional
    public Attempt addAttemptValidated(Attempt attempt) {
        areaCheck.checkHit(attempt);
        entityManager.merge(attempt);
        return attempt;
    }

    @Transactional
    public void addAttemptFromJsParams(int currentR) {
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        try {
            Double x = Double.parseDouble(params.get("x"));
            Double y = Double.parseDouble(params.get("y"));
            Double graphR = Double.parseDouble(params.get("r"));

            final Attempt attemptBean = new Attempt(
                    x / graphR * currentR,
                    y / graphR * currentR,
                    currentR
            );
            if validateAttempt(attemptBean) ? addAttemptValidated(attemptBean) : throw new ValidationException("Ошибка валидации");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public String collectToJson(Function<? super Attempt, Double> getter) {
        return new Gson().toJson(HitResultRepository.getLatestAttemptsList().stream().map(getter).collect(Collectors.toList()));
    }

    public String getX() {
        return collectToJson(Attempt::getX);
    }

    public String getY() {
        return collectToJson(Attempt::getY);
    }

    public String getR() {
        return collectToJson(Attempt::getR);
    }

    public String getHit() {
        return new Gson().toJson(HitResultRepository.getLatestAttemptsList().stream().map(Attempt::isResult).collect(Collectors.toList()));
    }

    public String getPointsCoordinates() {
        return new Gson().toJson(
                HitResultRepository.getLatestAttemptsList().stream().map(Attempt::getCoordinates).collect(Collectors.toList())
        );
    }

}
