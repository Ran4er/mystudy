package ru.ifmo.se.services.Validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.validation.ValidationException;

import java.io.Serializable;

@Named("validatePoints")
@ValidatePointsQualifier
@ApplicationScoped
public class ValidationPointsImpl implements ValidationPoints, Serializable {

    @Override
    public boolean validate(double x, double y, double r)  {
        try {
            return validateX(x) && validateY(y) && validateR(r);
        } catch (ValidationException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateX(double x) {
        return (x >= -5) && (x <= 3);
    }

    private boolean validateY(double y) {
        return (y >= -3) && (y <= 5);
    }

    private boolean validateR(double r) {
        return (r >= 1) && (r <= 3);
    }

}
