package ru.ifmo.se.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.ifmo.se.dto.AttemptBean;
import ru.ifmo.se.models.AttemptEntity;
import ru.ifmo.se.repositories.HitResultRepository;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Named("areaCheck")
@ApplicationScoped
@AreaCheckQualifier
public class AreaCheckImpl implements AreaCheck, Serializable {
  private final Validator validator;

  @Inject
  private HitResultRepository repository;

  public AreaCheckImpl() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Override
  public void checkHit(AttemptBean attempt) {
    validateAttempt(attempt);

    long startTime = System.nanoTime();
    attempt.setCreatedAt(new Date());

    boolean hit = attemptIsInArea(attempt);
    attempt.setResult(hit);
    attempt.setExecutionTime(System.nanoTime() - startTime);
  }

  private void validateAttempt(AttemptBean attempt) {
    Set<ConstraintViolation<AttemptBean>> violations = validator.validate(attempt);
    if (!violations.isEmpty()) {
      throw new IllegalArgumentException("Attempt data is invalid: " + violations);
    }
  }

  private boolean attemptIsInArea(AttemptBean attempt) {
    var x = attempt.getX();
    var y = attempt.getY();
    var r = attempt.getR();
    return attemptIsInRect(x, y, r) || attemptIsInTriangle(x, y, r) || attemptIsInSector(x, y, r);
  }

  private boolean attemptIsInRect(double x, double y, double r) {
    return (x >= 0 && y >= 0) && (x <= r && y <= r );
  }

  private boolean attemptIsInSector(double x, double y, double r) {
    return (x <= 0 && y <= 0) && (x * x + y * y <= r * r);
  }

  private boolean attemptIsInTriangle(double x, double y, double r) {
    return (x >= 0 && y < 0) && (x <= r / 2) && (y >= -r / 2) && (x - y <= r/2);
  }

  private AttemptEntity convertToEntity(AttemptBean attempt) {
    return new AttemptEntity(
            attempt.getX(),
            attempt.getY(),
            attempt.getR(),
            attempt.isResult(),
            attempt.getCreatedAt(),
            attempt.getExecutionTime()
    );
  }
}
