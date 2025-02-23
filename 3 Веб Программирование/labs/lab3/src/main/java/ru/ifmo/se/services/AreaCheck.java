package ru.ifmo.se.services;

import ru.ifmo.se.dto.AttemptBean;

import java.io.Serializable;

public interface AreaCheck extends Serializable {
    void checkHit(AttemptBean attempt);
}
