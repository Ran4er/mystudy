package ru.ifmo.se.dto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.*;
import ru.ifmo.se.models.AttemptEntity;

import java.io.Serializable;
import java.util.Date;

@Named("attemptBean")
@ApplicationScoped
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttemptBean implements Serializable {

    @Getter
    @AllArgsConstructor
    public static class Coordinates {
        private final double x;
        private final double y;
        private final double r;
        private final boolean result;
    }

    private double x;
    private double y;
    private double r;
    private boolean result;
    private Date createdAt;
    private Long executionTime;

    public AttemptBean(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Coordinates getCoordinates() {
        return new Coordinates(x, y, r, result);
    }

    public AttemptEntity toEntity() {
        return new AttemptEntity(x, y, r, result, createdAt, executionTime);
    }

    public static AttemptBean fromEntity(AttemptEntity entity) {
        AttemptBean bean = new AttemptBean(entity.getX(), entity.getY(), entity.getR());
        bean.setResult(entity.isResult());
        bean.setCreatedAt(entity.getCreatedAt());
        bean.setExecutionTime(entity.getExecutionTime());
        return bean;
    }
}
