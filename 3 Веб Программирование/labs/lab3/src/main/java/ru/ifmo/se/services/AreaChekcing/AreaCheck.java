package ru.ifmo.se.services.AreaChekcing;

import ru.ifmo.se.models.Attempt;

import java.io.Serializable;

public interface AreaCheck extends Serializable {
    void checkHit(Attempt hitResultBean);
}