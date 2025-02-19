package ru.ifmo.se.services.Validation;

import java.io.Serializable;

public interface ValidationPoints extends Serializable {

    boolean validate(double x, double y, double r);
}
