package ru.ifmo.se.services.AreaChekcing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import ru.ifmo.se.models.Attempt;

import java.io.Serializable;
import java.util.Date;

@Named("areaCheck")
@ApplicationScoped
@AreaCheckQualifier
public class AreaCheckImpl implements AreaCheck, Serializable {
    @Override
    public void checkHit(Attempt attempt) {
        long startTime = System.nanoTime();
        attempt.setCreatedAt(new Date(System.currentTimeMillis()));

        boolean hit = hitResultIsInArea(attempt);
        attempt.setResult(hit);
        attempt.setExecutionTime(System.nanoTime() - startTime);
    }

    private boolean hitResultIsInArea(Attempt hitResultBean) {
        Double x = hitResultBean.getX();
        Double y = hitResultBean.getY();
        Double r = hitResultBean.getR();
        return hitResultRect(x, y, r) || hitResultTriangle(x, y, r) || hitResultSector(x, y, r);
    }

    private boolean hitResultRect(double x, double y, double r) {
        return (x > 0 && y > 0) && (x <= r && y <= r);
    }

    private boolean hitResultTriangle(double x, double y, double r) {
        return (x > 0 && y <= 0) && (y >= r/2); // Testing
    }

    private boolean hitResultSector(double x, double y, double r) {
        return (x <= 0 && y <= 0) && (x*x + y*y <= r*r);
    }
}
